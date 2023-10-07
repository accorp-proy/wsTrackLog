package com.primax.web.svt;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reporte/*")
public class ReportServlet extends HttpServlet {

	private static final long serialVersionUID = -6803373446806901358L;

	private String reportPath;

	@Override
	public void init() throws ServletException {
		this.reportPath = File.separator + "pages" + File.separator + "jasper";
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String reqRep = request.getParameter("rptid");
		if (reqRep == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		@SuppressWarnings("unchecked")
		Map<String, String[]> params = request.getParameterMap();
		String path = getServletContext().getRealPath(reportPath);
		switch (reqRep) {
			case "4":
				try {

				} catch (Exception e) {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
					e.printStackTrace();
				}
				break;

		}
	}

	public void exportIt(ByteArrayOutputStream stream, HttpServletResponse response) {
		response.reset();
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline; filename=Reporte.pdf");
		BufferedOutputStream output = null;
		if (stream == null)
			return;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			output.write(stream.toByteArray());
			output.close();
		} catch (IOException e) {
			System.err.println("Error @ Export Report " + e.getMessage());
		}
	}
}
