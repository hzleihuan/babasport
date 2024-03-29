package cn.itcast.web.action.product;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import cn.itcast.bean.product.ProductInfo;

public class BuildHtmlFile {
	
	public static void createProductHtml(ProductInfo product, File saveDir){
		try {
			if(!saveDir.exists()) saveDir.mkdirs();
			VelocityContext context = new VelocityContext();
			context.put("product", product);
			Template template = Velocity.getTemplate("product/productview.vm");
			FileOutputStream outStream = new FileOutputStream(new File(saveDir, product.getId()+".html"));
			OutputStreamWriter writer =  new OutputStreamWriter(outStream,"UTF-8");
			BufferedWriter sw = new BufferedWriter(writer);
			template.merge(context, sw);
			sw.flush();
			sw.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
