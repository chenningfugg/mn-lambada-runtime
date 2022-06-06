package com.example;

import com.Customer;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.annotation.YuJaAutowired;
import com.google.gson.Gson;
import io.micronaut.function.aws.MicronautRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Collections;

public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Gson gson = new Gson();

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();

        logger.info("=================test=====================");
        logger.info(gson.toJson(input));
        Customer customer = new Customer();
        customer.setName("Steven");
        customer.setAge(23);

        logger.info(gson.toJson(customer));
        logger.info(Customer.class.getName());
        if (Customer.class.isAnnotationPresent(YuJaAutowired.class)) {
            logger.info("================has=======================");
        }
        for (Field field : Customer.class.getDeclaredFields()) {
            logger.info(field.getName());
            if (field.isAnnotationPresent(YuJaAutowired.class)) {
                field.setAccessible(true);
                logger.info("has field:{}", field.getName());
            }
        }
        String json = gson.toJson(Collections.singletonMap("message", "Hello World"));
        response.setStatusCode(200);
        response.setBody(json);

        return response;
    }
}
