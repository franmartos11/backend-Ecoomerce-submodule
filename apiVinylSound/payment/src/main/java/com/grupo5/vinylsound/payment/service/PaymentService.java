package com.grupo5.vinylsound.payment.service;

import com.grupo5.vinylsound.payment.exception.InternalServerErrorException;
import com.grupo5.vinylsound.payment.model.Product;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

  @Value("${mercado.pago.access.token}")
  private String mercadoPagoAccessToken;

  private void setConnectionMercadoPago() {
    MercadoPagoConfig.setAccessToken(mercadoPagoAccessToken);
  }

  private PreferenceRequest getPreferenceRequest(List<Product> products) {
    List<PreferenceItemRequest> items = new ArrayList<>();
    products.forEach(product -> {
      var itemRequest = PreferenceItemRequest.builder()
        .id(product.id())
        .title(product.title())
        .description(product.description())
        .pictureUrl(product.image_url())
        .categoryId(product.category())
        .quantity(product.quantity())
        .currencyId("ARS")
        .unitPrice(BigDecimal.valueOf(product.price()))
        .build();
      items.add(itemRequest);
    });
    return PreferenceRequest.builder()
      .items(items)
      .build();
  }

  public String pay(List<Product> products) throws InternalServerErrorException {
    setConnectionMercadoPago();
    PreferenceRequest preferenceRequest = getPreferenceRequest(products);
    PreferenceClient preferenceClient = new PreferenceClient();
    try {
      Preference preference = preferenceClient.create(preferenceRequest);
      return preference.getId();
    } catch (MPException | MPApiException exception) {
      throw new InternalServerErrorException(exception.getMessage());
    }
  }

}