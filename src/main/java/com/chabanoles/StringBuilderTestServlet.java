package com.chabanoles;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bonitasoft.web.toolkit.client.common.json.JSonSerializer;
import org.json.simple.JSONArray;

public class StringBuilderTestServlet  extends HttpServlet {


    private static final String CUSTOM_SERIALIZER = "custom";

    public static void main(String[] args) {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
           long iterations = getNumberOfIterations(req);
            String serializer = getSerializerName(req);
            List<?> list = buildList(iterations);

            long start = System.currentTimeMillis();
            String response = serializeWith(serializer, list);
            long end = System.currentTimeMillis();

            sendResponse(resp,response,end - start);

        } catch (Exception e) {
            throw new ServletException("Unable to compose big String:", e);
        }

    }

    private String serializeWith(String serializer, List<?> list) {
        if(CUSTOM_SERIALIZER.equals(serializer)) {
            return JSonSerializer.serializeCollection(list);
        }
        return JSONArray.toJSONString(list);
    }

    private String getSerializerName(HttpServletRequest req) {
        String s = req.getParameter("serializer");
        if(s == null || "".equals(s)) {
            return CUSTOM_SERIALIZER;
        }
        return s;
    }

    private List<?> buildList(long iterations) {
        List list = new ArrayList<>();
        for (int i = 0; i < iterations; i++) {
            list.add(createPageAsMap(i));
        }
        return list;
    }

    private HashMap createPageAsMap(int pageId) {
        HashMap<String, Serializable> page = new HashMap<>();
        page.put("processDefinitionId", 9076375433363123096L);
        page.put("updatedBy", 55);
        page.put("urlToken", "custompage_1nCancelNotificationForm");
        page.put("displayName", "1nCancelNotificationForm page");
        page.put("lastUpdateDate", new Date().toString());
        page.put("description", "1nCancelNotificationForm page generated with Bonita BPM UI designer");
        page.put("creationDate", new Date().toString());
        page.put("contentName", "custompage_1nCancelNotificationForm.zip");
        page.put("isHidden", false);
        page.put("createdBy",55 );
        page.put("isProvided",false );
        page.put("id", pageId);
        page.put("contentType", "form");
        return page;

    }

    private long getNumberOfIterations(HttpServletRequest req) {
        return Long.parseLong(req.getParameter("iterations"));
    }

    private void sendResponse(HttpServletResponse resp, String longText, long duration) throws ServletException {
        PrintWriter out;
        try {
            out = resp.getWriter();
            out.println("<html><body>");
            out.println("<span>");
            out.println("Text created in " + duration + " milliseconds <br><hr>");
            out.println("</span>");
            out.println("<span>");
            out.println(longText);
            out.println("</span>");
            out.println("</body></html>");

        } catch (IOException e) {
            throw new ServletException("Text generated but not able to write the response.", e);
        }
    }
}
