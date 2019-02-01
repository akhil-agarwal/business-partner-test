package com.sap.cloud.s4hana.examples.addressmgr;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.sap.cloud.sdk.cloudplatform.logging.CloudLoggerFactory;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

import com.sap.cloud.sdk.s4hana.datamodel.odata.helper.Order;
import com.sap.cloud.sdk.s4hana.datamodel.odata.namespaces.businesspartner.BusinessPartner;
import com.sap.cloud.sdk.s4hana.datamodel.odata.services.DefaultBusinessPartnerService;

@WebServlet("/api/business-partners")
public class BusinessPartnerServlet extends HttpServlet {
    private static final org.slf4j.Logger logger = CloudLoggerFactory
            .getLogger(BusinessPartnerServlet.class);

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
        throws ServletException, IOException {
        final List<BusinessPartner> result;

        try {
            result = new DefaultBusinessPartnerService()
                    .getAllBusinessPartner()
                    .select(BusinessPartner.BUSINESS_PARTNER,
                        BusinessPartner.LAST_NAME,
                        BusinessPartner.FIRST_NAME)
                    .orderBy(BusinessPartner.LAST_NAME, Order.ASC)
                    .execute();
        } catch (ODataException e) {
            logger.error("Error retrieving Business Partner Information");
            throw new ServletException(e);
        }

        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(result));
    }
}
