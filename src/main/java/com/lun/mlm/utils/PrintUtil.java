package com.lun.mlm.utils;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.lun.mlm.model.Order;
import com.lun.mlm.model.OrderDetail;

public class PrintUtil implements Printable {
	
	private Order order; 

	public PrintUtil(Order order) {
		super();
		this.order = order;
	}

	@Override
	    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {  
	  
	        if (page > 0) {  
	            return NO_SUCH_PAGE;  
	        }  
	        List<OrderDetail> list = order.getList();
	        Graphics2D g2d = (Graphics2D) g;  
	        g2d.setFont(new Font("Default", Font.PLAIN, 14));  
	        g2d.drawString("zm", 80, 10);
	        g2d.setFont(new Font("Default", Font.PLAIN, 10));  
	        g2d.drawString("单号:"+order.getId(), 12, 20);  
	        g2d.drawString("姓名:" +order.getTrueName(), 12, 35);  
	        g2d.drawString("手机号:"+order.getPhone(), 12, 50);  
	        g2d.drawString("地址:"+order.getAddr(), 12, 65);  
	        g2d.drawString("送达时间:"+order.getSendTimeStr(), 12, 80);  
	        g2d.drawString("-------------------------------------", 12, 95);  
	        g2d.drawString("订单详情:", 12, 110);  
	        int y = 110;
	        for(int i=0;i<list.size();i++){
	        	OrderDetail od = list.get(i);
	        	y += 15;
		        g2d.drawString(od.getDishName()+"     "+od.getPrice()+" × "+od.getDishNum(), 12, y);  
	        }
	        g2d.drawString("-------------------------------------", 12, y+15);  
	        g2d.drawString("总计:" +order.getPay(), 12, y+30);  
//			try {
//				File file = new File("D://qrcode.jpg");
//				BufferedImage bi = ImageIO.read(file);
//				g2d.drawImage(bi, 80, y+45, 60, 60, null);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			g2d.drawString("微信订餐", 80, y+110);  
	        return PAGE_EXISTS;  
	    }  
	  
	    public static void main(String[] args) {  
	  
	        int height = 175 + 3 * 15 + 20;  
	  
	        // 通俗理解就是书、文档  
	        Book book = new Book();  
	  
	        // 打印格式  
	        PageFormat pf = new PageFormat();  
	        pf.setOrientation(PageFormat.PORTRAIT);  
	  
	        // 通过Paper设置页面的空白边距和可打印区域。必须与实际打印纸张大小相符。  
	        Paper p = new Paper();  
	        p.setSize(230, height);  
	        p.setImageableArea(5, -20, 230, height + 20);  
	        pf.setPaper(p);  
	  
	        // 把 PageFormat 和 Printable 添加到书中，组成一个页面  
	        Order order = new Order();
	        book.append(new PrintUtil(order), pf);  
	  
	        // 获取打印服务对象  
	        PrinterJob job = PrinterJob.getPrinterJob();  
	        job.setPageable(book);  
	        try {  
	            job.print();  
	        } catch (PrinterException e) {  
	            System.out.println("================打印出现异常");  
	        }  
	  
	    }  
}
