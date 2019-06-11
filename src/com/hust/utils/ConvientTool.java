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
		SystemTray systemTray = SystemTray.getSystemTray();// ���ϵͳ���̶���
		try {
			systemTray.add(trayIcon);
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// ������ͼƬ��ӵ�ϵͳ������
	}
	public ConvientTool() {
//		super();
//		setTitle("ʹ��ϵͳ����");
//		setBounds(100, 100, 500, 375);
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		final JLabel label = new JLabel();
//		label.setForeground(new Color(0, 128, 0));
//		label.setFont(new Font("", Font.BOLD, 16));
//		label.setHorizontalAlignment(SwingConstants.CENTER);
//		label.setText("��鿴ϵͳ�����еı仯��");
//		getContentPane().add(label, BorderLayout.CENTER);
		
	}
 
	List<Component> lists = new ArrayList<Component>();
	Map<String, Component> map = new HashMap<String, Component>();
	private int dialogIndex = 0;
 
	TrayIcon trayIcon = null;
	public TrayIcon addIconToTray() throws AWTException {
		if (SystemTray.isSupported()) {// �ж�ϵͳ�Ƿ�֧��ϵͳ���̹���
 
			URL resource = this.getClass().getResource("1.jpg");// ���ͼƬ·��
			ImageIcon icon = new ImageIcon(resource);// ����ͼƬ����
 
			PopupMenu popupMenu = new PopupMenu();// ���������˵�����
			MenuItem item = new MenuItem("�˳�");// �������˳����˵���Ŀ����
			item.addActionListener(new ActionListener() {// Ϊ�˵���Ŀ��Ӷ���������
				public void actionPerformed(ActionEvent e) {
					System.exit(0);// �˳�ϵͳ
				}
			});
			MenuItem item1 = new MenuItem("�½���ǩֽ");
			item1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JDialog dialog = new JDialog();
					JTextArea area = new JTextArea();
					area.setBackground(new Color(255, 255, 128));
					area.setBorder(BorderFactory.createLineBorder(Color.yellow,
							2));
					area.setToolTipText("˫�����أ�����ɾ���˱�ǩֽ");
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
			MenuItem item2 = new MenuItem("���ر�ǩֽ");
			MenuItem item3 = new MenuItem("ˢ��");
			
			item2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					for (int i = 0; i < lists.size(); i++) {
						((JDialog) lists.get(i)).setVisible(false);
					}
				}
			});
			PopupMenu pagesMenu = new PopupMenu("�����ǩֽ");
			addPage(pagesMenu);
			popupMenu.add(item1);// �����˳����˵���Ŀ��ӵ������˵���
			popupMenu.add(pagesMenu);
			popupMenu.add(item2);
			popupMenu.add(item3);
			popupMenu.add(item);// �����˳����˵���Ŀ��ӵ������˵���
			 trayIcon = new TrayIcon(icon.getImage(), "ʹ��ϵͳ����",
					popupMenu);// ��������ͼƬ����
			 item3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						SystemTray systemTray = SystemTray.getSystemTray();// ���ϵͳ���̶���
						try {
							systemTray.remove(trayIcon);
							addComplete(addIconToTray());
						} catch (AWTException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}// ������ͼƬ��ӵ�ϵͳ������
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
				// �����������
			} else if (evt.getClickCount() == 2) {
				// �������˫��
				dialog.setVisible(false);
			}
		}
	}
	public void addPage(PopupMenu pagesMenu) {
		for (Entry<String, Component> entity : map.entrySet()) {
			// PopupMenu subMenu = new PopupMenu(entity);
			MenuItem item = new MenuItem(entity.getKey() + " �鿴");
			MenuItem item2 = new MenuItem(entity.getKey() + " ɾ��");
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
