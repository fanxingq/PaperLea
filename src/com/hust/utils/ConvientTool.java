package com.hust.utils;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Rectangle;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
 
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
 
public class ConvientTool extends JFrame {
 
	public static void main(String args[]) {
		ConvientTool frame = new ConvientTool();
//		frame.setVisible(true);
		try {
//			frame.addIconToTray();
			frame.addComplete(frame.addIconToTray());
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
 
	public void addComplete(TrayIcon trayIcon)
	{
		SystemTray systemTray = SystemTray.getSystemTray();// 获得系统托盘对象
		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 将托盘图片添加到系统托盘中
	}
	public ConvientTool() {
//		super();
//		setTitle("使用系统托盘");
//		setBounds(100, 100, 500, 375);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		final JLabel label = new JLabel();
//		label.setForeground(new Color(0, 128, 0));
//		label.setFont(new Font("", Font.BOLD, 16));
//		label.setHorizontalAlignment(SwingConstants.CENTER);
//		label.setText("请查看系统托盘中的变化！");
//		getContentPane().add(label, BorderLayout.CENTER);
		
	}
 
	List<Component> lists = new ArrayList<Component>();
	Map<String, Component> map = new HashMap<String, Component>();
	private int dialogIndex = 0;
 
	TrayIcon trayIcon = null;
	public TrayIcon addIconToTray() throws AWTException {
		if (SystemTray.isSupported()) {// 判断系统是否支持系统托盘功能
 
			URL resource = this.getClass().getResource("1.jpg");// 获得图片路径
			ImageIcon icon = new ImageIcon(resource);// 创建图片对象
 
			PopupMenu popupMenu = new PopupMenu();// 创建弹出菜单对象
			MenuItem item = new MenuItem("退出");// 创建“退出”菜单项目对象
			item.addActionListener(new ActionListener() {// 为菜单项目添加动作监听器
				public void actionPerformed(ActionEvent e) {
					System.exit(0);// 退出系统
				}
			});
			MenuItem item1 = new MenuItem("新建便签纸");
			item1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog dialog = new JDialog();
					JTextArea area = new JTextArea();
					area.setBackground(new Color(255, 255, 128));
					area.setBorder(BorderFactory.createLineBorder(Color.yellow,
							2));
					area.setToolTipText("双击隐藏，三击删除此便签纸");
					JLabel label = new JLabel("        ");
					dialog.add(label, BorderLayout.NORTH);
					dialog.add(area, BorderLayout.CENTER);
					dialog.setSize(250, 300);
					dialog.setUndecorated(true);
					dialog.setResizable(true);
					Dimension screenSize = Toolkit.getDefaultToolkit()
							.getScreenSize();
					Dimension frameSize = dialog.getSize();
					dialog.setLocation((screenSize.width - frameSize.width) / 1
							- 100 + 10 * dialogIndex,
							(screenSize.height - frameSize.height) / 2 + 10
									* dialogIndex);
 
					MouseAdapter mouseAdapter = new WindowDragListener(dialog);
					dialog.addMouseListener(mouseAdapter);
					dialog.addMouseMotionListener(mouseAdapter);
//					dialog.setTitle(""+dialogIndex);
					dialog.setVisible(true);
					lists.add(dialog);
					map.put("" + dialogIndex, dialog);
					dialogIndex++;
				}
			});
			MenuItem item2 = new MenuItem("隐藏便签纸");
			MenuItem item3 = new MenuItem("刷新");
			
			item2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < lists.size(); i++) {
						((JDialog) lists.get(i)).setVisible(false);
					}
				}
			});
			PopupMenu pagesMenu = new PopupMenu("管理便签纸");
			addPage(pagesMenu);
			popupMenu.add(item1);// 将“退出”菜单项目添加到弹出菜单中
			popupMenu.add(pagesMenu);
			popupMenu.add(item2);
			popupMenu.add(item3);
			popupMenu.add(item);// 将“退出”菜单项目添加到弹出菜单中
			 trayIcon = new TrayIcon(icon.getImage(), "使用系统托盘",
					popupMenu);// 创建托盘图片对象
			 item3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						SystemTray systemTray = SystemTray.getSystemTray();// 获得系统托盘对象
						try {
							systemTray.remove(trayIcon);
							addComplete(addIconToTray());
						} catch (AWTException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}// 将托盘图片添加到系统托盘中
					}
				});
		}
		return trayIcon;
	}
	
	
	class WindowDragListener extends MouseAdapter {
		private JDialog dialog;
		private Point pressPoint = new Point();
		public WindowDragListener() {
			// TODO Auto-generated constructor stub
		}
		public WindowDragListener(JDialog dialog) {
			this.dialog = dialog;
		}
		@Override
		public void mousePressed(MouseEvent e) {
			pressPoint = e.getPoint();
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			pressPoint = new Point();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			Rectangle r = dialog.getBounds();
			dialog.setLocation(r.x + (e.getX() - pressPoint.x), r.y
					+ (e.getY() - pressPoint.y));
		}
		
		public void mouseClicked(MouseEvent evt) {
			if (evt.getClickCount() == 3) {
//				lists.remove(dialog);
//				map.remove(dialog.getTitle());
//				dialog.dispose();
				// 处理鼠标三击
			} else if (evt.getClickCount() == 2) {
				// 处理鼠标双击
				dialog.setVisible(false);
			}
		}
	}
	public void addPage(PopupMenu pagesMenu) {
		for (Entry<String, Component> entity : map.entrySet()) {
			// PopupMenu subMenu = new PopupMenu(entity);
			MenuItem item = new MenuItem(entity.getKey() + " 查看");
			MenuItem item2 = new MenuItem(entity.getKey() + " 删除");
			final String tempString = entity.getKey();
			item.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (map.get(tempString) instanceof JDialog) {
						JDialog dialog = (JDialog) map.get(tempString);
						dialog.setVisible(true);
					}
				}
			});
			item2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (map.get(tempString) instanceof JDialog) {
						JDialog dialog = (JDialog) map.get(tempString);
						lists.remove(dialog);
						dialog.dispose();
					}
					map.remove(tempString);
				}
			});
			pagesMenu.add(item);
			pagesMenu.add(item2);
		}
	}
}
