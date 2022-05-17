package net.runelite.client.plugins.paistisuite.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.plugins.paistisuite.iPaistiSuite;
import static net.runelite.client.plugins.paistisuite.ui.PaistiSuitePanel.PANEL_BACKGROUND_COLOR;
import static net.runelite.client.ui.PluginPanel.PANEL_WIDTH;

import net.runelite.client.plugins.paistisuite.api.PLogin;
import net.runelite.client.util.DeferredDocumentChangedListener;

public class PaistiSuiteAccountPanel extends JPanel
{
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(PANEL_WIDTH, super.getPreferredSize().height);
	}

	private final ConfigManager configManager;
	private final JPanel contentPanel = new JPanel(new GridLayout(6, 0));

	PaistiSuiteAccountPanel(iPaistiSuite suite)
	{
		this.configManager = suite.getConfigManager();

		setupDefaults();
		setLayout(new BorderLayout());
		setBackground(PANEL_BACKGROUND_COLOR);

		init();
		add(contentPanel, BorderLayout.NORTH);
	}

	private void init()
	{
		contentPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

		contentPanel.add(new JLabel("Username"));

		final JTextField usernameField = new JTextField();
		usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		usernameField.setText(configManager.getConfiguration(iPaistiSuite.CONFIG_GROUP, "account-username"));
		DeferredDocumentChangedListener usernameListener = new DeferredDocumentChangedListener();
		usernameListener.addChangeListener(e ->
			configManager.setConfiguration(iPaistiSuite.CONFIG_GROUP, "account-username", usernameField.getText()));
		usernameField.getDocument().addDocumentListener(usernameListener);

		contentPanel.add(usernameField);

		contentPanel.add(new JLabel("Password"));

		final JPasswordField passwordField = new JPasswordField();
		passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		passwordField.setText(configManager.getConfiguration(iPaistiSuite.CONFIG_GROUP, "account-password"));
		DeferredDocumentChangedListener passwordListener = new DeferredDocumentChangedListener();
		passwordListener.addChangeListener(e ->
			configManager.setConfiguration(iPaistiSuite.CONFIG_GROUP, "account-password", String.valueOf(passwordField.getPassword())));
		passwordField.getDocument().addDocumentListener(passwordListener);

		contentPanel.add(passwordField);

		contentPanel.add(new JLabel("Bank Pin"));

		final JPasswordField pinField = new JPasswordField();
		pinField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		pinField.setText(configManager.getConfiguration(iPaistiSuite.CONFIG_GROUP, "account-bankpin"));
		DeferredDocumentChangedListener pinListener = new DeferredDocumentChangedListener();
		pinListener.addChangeListener(e ->
			configManager.setConfiguration(iPaistiSuite.CONFIG_GROUP, "account-bankpin", String.valueOf(pinField.getPassword())));
		pinField.getDocument().addDocumentListener(passwordListener);

		contentPanel.add(pinField);

		final JButton loginButton = new JButton();
		loginButton.setText("Test Login");
		loginButton.addActionListener((e) -> {
			PLogin.login();
		});
		contentPanel.add(loginButton);
	}

	private void setupDefaults()
	{
		if (configManager.getConfiguration(iPaistiSuite.CONFIG_GROUP, "account-username") == null)
		{
			configManager.setConfiguration(iPaistiSuite.CONFIG_GROUP, "account-username", "");
		}

		if (configManager.getConfiguration(iPaistiSuite.CONFIG_GROUP, "account-password") == null)
		{
			configManager.setConfiguration(iPaistiSuite.CONFIG_GROUP, "account-password", "");
		}

		if (configManager.getConfiguration(iPaistiSuite.CONFIG_GROUP, "account-bankpin") == null)
		{
			configManager.setConfiguration(iPaistiSuite.CONFIG_GROUP, "account-bankpin", "");
		}
	}


}
