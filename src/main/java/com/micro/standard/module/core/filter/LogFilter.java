package com.micro.standard.module.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Order(value = Ordered.LOWEST_PRECEDENCE)
@WebFilter(urlPatterns = "/*")
public class LogFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		IoUtil.copy(request.getInputStream(), baos);
//
//		InputStream inReq = new ByteArrayInputStream(baos.toByteArray());
//		String str1 = IoUtil.readUtf8(inReq);
//		log.info("入参:{}", str1);

//		OutputStream out = response.getOutputStream();
//		BufferedOutputStream bos = new BufferedOutputStream(out);
//		IoUtil.getUtf8Writer(bos);

		chain.doFilter(request, response);

//		if (StringUtils.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE, response.getContentType())) {
//			BufferedOutputStream bos1 = new BufferedOutputStream(response.getOutputStream());
//
//			byte[] bys = new byte[1024];
//			IoUtil.write(bos1, false, bys);
//			
// 
//			log.info("返回:{}", bys.toString());
//		}

	}

}
