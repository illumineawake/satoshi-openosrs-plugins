package net.runelite.client.plugins.paistisuite.api;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.plugins.paistisuite.iPaistiSuite;
import net.runelite.client.plugins.paistisuite.api.types.PItem;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class PInventory {
    public static ItemComposition getItemDef(WidgetItem item) {
        return PUtils.clientOnly(() -> iPaistiSuite.getInstance().itemManager.getItemComposition(item.getId()), "getItemDef");
    }

    public static ItemComposition getItemDef(int id) {
        return PUtils.clientOnly(() -> iPaistiSuite.getInstance().itemManager.getItemComposition(id), "getItemDef");
    }

    public static ItemComposition getItemDef(Item item) {
        return PUtils.clientOnly(() -> iPaistiSuite.getInstance().itemManager.getItemComposition(item.getId()), "getItemDef");
    }

    private static Future<ItemComposition> getFutureItemDef(WidgetItem item) {
        if (item == null) return null;

        return iPaistiSuite.getInstance().clientExecutor.schedule(() -> iPaistiSuite.getInstance().itemManager.getItemComposition(item.getId()), "getItemDef");
    }

    private static WidgetItem createWidgetItem(Widget item) {
        boolean isDragged = item.isWidgetItemDragged(item.getItemId());

        int dragOffsetX = 0;
        int dragOffsetY = 0;

        if (isDragged) {
            Point p = item.getWidgetItemDragOffsets();
            dragOffsetX = p.getX();
            dragOffsetY = p.getY();
        }
        // set bounds to same size as default inventory
        Rectangle bounds = item.getBounds();
        bounds.setBounds(bounds.x - 1, bounds.y - 1, 32, 32);
        Rectangle dragBounds = item.getBounds();
        dragBounds.setBounds(bounds.x + dragOffsetX, bounds.y + dragOffsetY, 32, 32);

        return new WidgetItem(item.getItemId(), item.getItemQuantity(), item.getIndex(), bounds, item, dragBounds);
    }

    public static Collection<WidgetItem> getWidgetItems() {
        Widget geWidget = PUtils.getClient().getWidget(WidgetInfo.GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER);

        boolean geOpen = geWidget != null && !geWidget.isHidden();
        boolean bankOpen = !geOpen && PUtils.getClient().getItemContainer(InventoryID.BANK) != null;

        Widget inventoryWidget = PUtils.getClient().getWidget(
                bankOpen ? WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER :
                        geOpen ? WidgetInfo.GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER :
                                WidgetInfo.INVENTORY
        );

        if (inventoryWidget == null) {
            return new ArrayList<>();
        }

        if (!bankOpen && !geOpen && inventoryWidget.isHidden()) {
            refreshInventory();
        }

        Widget[] children = inventoryWidget.getDynamicChildren();

        if (children == null) {
            return new ArrayList<>();
        }

        Collection<WidgetItem> widgetItems = new ArrayList<>();
        for (Widget item : children) {
            if (item.getItemId() != 6512) {
                widgetItems.add(createWidgetItem(item));
            }
        }

        return widgetItems;
    }

    public static void refreshInventory() {
        if (PUtils.getClient().isClientThread()) {
            PUtils.getClient().runScript(6009, 9764864, 28, 1, -1);
        } else {
            iPaistiSuite.getInstance().clientExecutor.schedule(() -> PUtils.getClient().runScript(6009, 9764864, 28, 1, -1), "runScript6009");
        }
    }

    public static WidgetItem getWidgetItemInSlot(int slot) {
        Widget geWidget = PUtils.getClient().getWidget(WidgetInfo.GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER);

        boolean geOpen = geWidget != null && !geWidget.isHidden();
        boolean bankOpen = !geOpen && PUtils.getClient().getItemContainer(InventoryID.BANK) != null;

        Widget inventoryWidget = PUtils.getClient().getWidget(
                bankOpen ? WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER :
                        geOpen ? WidgetInfo.GRAND_EXCHANGE_INVENTORY_ITEMS_CONTAINER :
                                WidgetInfo.INVENTORY
        );

        if (inventoryWidget == null) {
            return new WidgetItem(-1, 0, slot, null, null, null);
        }

        if (!bankOpen && !geOpen && inventoryWidget.isHidden()) {
            refreshInventory();
        }

        Widget[] children = inventoryWidget.getDynamicChildren();

        if (children == null || slot >= children.length || slot < 0) {
            return new WidgetItem(-1, 0, slot, null, null, null);
        }

        if (children[slot].getItemId() == 6512) {
            return new WidgetItem(-1, 0, slot, null, null, null);
        }

        return createWidgetItem(children[slot]);
    }

    public static boolean isFull() {
        return getEmptySlots() <= 0;
    }

    public static boolean isEmpty() {
        return getEmptySlots() >= 28;
    }

    public static Integer getEmptySlots() {
        return PUtils.clientOnly(() -> {
            Widget inventoryWidget = PUtils.getClient().getWidget(WidgetInfo.INVENTORY);
            if (inventoryWidget != null) {
                return 28 - getWidgetItems().size();
            } else {
                return -1;
            }
        }, "getEmptySlots");
    }

    public static List<PItem> getAllItems() {
        return PUtils.clientOnly(() -> {
            Widget inventoryWidget = PUtils.getClient().getWidget(WidgetInfo.INVENTORY);
            if (inventoryWidget == null) return new ArrayList<PItem>();
            Collection<WidgetItem> widgetItems = getWidgetItems();
            List<PItem> pItems = widgetItems
                    .stream()
                    .map(PItem::new)
                    .collect(Collectors.toList());
            return pItems;
        }, "getAllPItems");
    }

    public static int getCount(String name) {
        int count = 0;
        List<PItem> items = getAllItems();
        for (PItem i : items) {
            if (i.getDefinition().getName().equalsIgnoreCase(name)) {
                count += i.getQuantity();
            }
        }
        return count;
    }

    public static int getCount(int id) {
        int count = 0;
        List<PItem> items = getAllItems();
        for (PItem i : items) {
            if (i.getId() == id) {
                count += i.getQuantity();
            }
        }
        return count;
    }

    public static List<PItem> findAllItems(Predicate<PItem> filter) {
        return getAllItems()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public static PItem findItem(Predicate<PItem> filter) {
        return getAllItems()
                .stream()
                .filter(filter)
                .findFirst()
                .orElse(null);
    }

    public static List<PItem> getEquipmentItems() {
        return PUtils.clientOnly(() -> {
            List<PItem> equippedPItems = new ArrayList<PItem>();
            ItemContainer container = PUtils.getClient().getItemContainer(InventoryID.EQUIPMENT);
            if (container == null) return equippedPItems;
            Item[] eqitems = PUtils.getClient().getItemContainer(InventoryID.EQUIPMENT).getItems();
            int slot = 0;
            for (Item i : eqitems) {
                if (i.getId() != -1) equippedPItems.add(PItem.fromEquipmentItem(i, slot));
                slot++;
            }
            return equippedPItems;
        }, "getEquippedPItems");
    }

    public static List<PItem> findAllEquipmentItems(Predicate<PItem> filter) {
        List<PItem> eq = getEquipmentItems();
        if (eq == null) return new ArrayList<PItem>();
        return eq
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public static PItem findEquipmentItem(Predicate<PItem> filter) {
        List<PItem> eq = getEquipmentItems();
        if (eq == null) return null;
        return eq
                .stream()
                .filter(filter)
                .findFirst()
                .orElse(null);
    }

    public static List<Item> legacyGetEquipmentItems() {
        List<Item> equipped = new ArrayList<>();
        Item[] items = null;
        if (PUtils.getClient().isClientThread()) {
            items = PUtils.getClient().getItemContainer(InventoryID.EQUIPMENT).getItems();
        } else {
            try {
                items = iPaistiSuite.getInstance().clientExecutor.scheduleAndWait(() -> {
                    return PUtils.getClient().getItemContainer(InventoryID.EQUIPMENT).getItems();
                }, "getEquippedItems");
            } catch (Exception e) {
            }
        }

        if (items == null) return equipped;
        for (Item item : items) {
            if (item.getId() == -1 || item.getId() == 0) {
                continue;
            }
            equipped.add(item);
        }
        return equipped;
    }

    public static int getEquipmentCount(int equipmentId) {
        int count = 0;
        List<Item> equipment = legacyGetEquipmentItems();

        for (Item i : equipment) {
            if (i.getId() == equipmentId) {
                count += i.getQuantity();
            }
        }

        return count;
    }
}
