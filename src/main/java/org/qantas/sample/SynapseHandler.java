package org.qantas.sample;

import com.fasterxml.jackson.databind.util.TypeKey;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.context.MessageContextConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.synapse.AbstractSynapseHandler;
import org.apache.synapse.MessageContext;
import org.apache.synapse.config.SynapseConfiguration;
import org.apache.synapse.core.axis2.Axis2MessageContext;
import org.apache.synapse.core.axis2.Axis2SynapseEnvironment;

public class SynapseHandler extends AbstractSynapseHandler {

    @Override
    public boolean handleRequestInFlow(MessageContext messageContext) {


        String api = (String) messageContext.getProperty("REST_FULL_REQUEST_PATH");
        if(api != null && api.startsWith("/api")){

            String restFullRequestPath = ((String) messageContext.getProperty("REST_FULL_REQUEST_PATH")).replaceFirst("/api", "");
            messageContext.setProperty("REST_FULL_REQUEST_PATH",restFullRequestPath);

            org.apache.axis2.context.MessageContext axis2MessageContext = ((Axis2MessageContext) messageContext).getAxis2MessageContext();

            String restUrlPostfix = ((String) axis2MessageContext.getProperty("TransportInURL")).replace("/api/", "");
            axis2MessageContext.setProperty("TransportInURL",restFullRequestPath);
            axis2MessageContext.setProperty("REST_URL_POSTFIX",restUrlPostfix);

            ((Axis2MessageContext) messageContext).setAxis2MessageContext(axis2MessageContext);

        }


        return true;
    }

    @Override
    public boolean handleRequestOutFlow(MessageContext messageContext) {
        return true;
    }

    @Override
    public boolean handleResponseInFlow(MessageContext messageContext) {
        return true;
    }

    @Override
    public boolean handleResponseOutFlow(MessageContext messageContext) {
        return true;
    }
}
