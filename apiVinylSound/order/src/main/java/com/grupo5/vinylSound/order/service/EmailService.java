package com.grupo5.vinylSound.order.service;

import com.grupo5.vinylSound.order.exception.NotFoundException;
import com.grupo5.vinylSound.order.model.Order;
import com.grupo5.vinylSound.order.model.OrderProduct;
import com.grupo5.vinylSound.order.repository.feign.CatalogClientFeign;
import com.grupo5.vinylSound.order.repository.feign.UserClientFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${email.sender}")
    private String emailAdmin;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private UserClientFeign userClientFeign;
    @Autowired
    private CatalogClientFeign catalogClientFeign;


    @Async
    public void sendEmailToUserOrder(Order order, Set<OrderProduct> products) throws MessagingException, NotFoundException {
        var response = userClientFeign.findById(order.getIdUser());
        if (response.getStatusCode().isError()) {
            throw new NotFoundException("No se ha encontrado el usuario con el id: " + order.getIdUser());
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());

        StringBuilder message = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="es">
                    
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Detalle del Pedido</title>
                    <style>
                        body {
                            font-family: "Roboto", sans-serif;
                            margin: 0;
                            padding: 0;
                            background-color: #1e1719;
                            color: #f2eede;
                        }
                    
                        h1, h2, h3 {
                            color: #f2eede;
                        }
                    
                        h2 {
                            font-size: 24px;
                        }
                    
                        h3 {
                            font-size: 20px;
                        }
                    
                        p {
                            font-size: 16px;
                            line-height: 1.5;
                        }
                    
                        ul {
                            list-style-type: none;
                            padding: 0;
                            margin: 0;
                        }
                    
                        ul li {
                            font-size: 16px;
                            margin-bottom: 10px;
                        }
                    
                        strong {
                            font-weight: bold;
                        }
                    
                        /* Estilo del div principal */
                        .container {
                            max-width: 600px;
                            margin: 0 auto;
                            padding: 20px;
                        }
                    
                        header {
                            background-color: #150d10;
                            padding: 10px 0;
                            text-align: center;
                        }
                    
                        header img {
                            max-width: 100%;
                            height: auto;
                        }
                    
                        footer {
                            text-align: center;
                            margin-top: 20px;
                            background-color: #150d10;
                            color: #f2eede;
                            padding: 10px 0;
                        }
                    
                        footer img {
                            max-width: 100%;
                            height: auto;
                        }
                    </style>
                </head>
                    
                <body style="background-color: #1e1719; color: #f2eede; font-family: "Roboto", sans-serif; margin: 0; padding: 0;">
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#88202b">
                        <tr>
                            <td style="padding: 10px;">
                                <img src="https://res.cloudinary.com/dnb5fwh6o/image/upload/v1711230036/Logo_waig2q.png" alt="Logo" style="width: 3.5rem; display: block;">
                            </td>
                        </tr>
                    </table>
                        
                            <table width="100%" border="0" cellspacing="0" cellpadding="20" bgcolor="#1e1719">
                                <tr>
                                    <td>
                                    <br />
                                        <h2 style="color: #f2eede;">Hola!</h2>
                                        <p style="font-size: 16px; line-height: 1.5;">Ya hemos recibido tu pedido. Pronto te estaremos visitando!</p>
                                        
                                        <br />
                                        <br />
                                        
                                        <h3 style="color: #f2eede;">Detalle del Pedido:</h3>
                                        <br />
                                        <ul>
                """);

        for (OrderProduct orderProduct : products) {
            var responseProduct = catalogClientFeign.findById(orderProduct.getId().getIdProduct());
            if (responseProduct.getStatusCode().isError()) {
                throw new NotFoundException("No se ha encontrado el producto con el id: " + orderProduct.getId().getIdProduct());
            }
            message
                    .append("<li style=\"font-size: 16px; margin-bottom: 10px;\">")
                    .append(Objects.requireNonNull(responseProduct.getBody()).title())
                    .append("  $");
            message
                    .append(Objects.requireNonNull(responseProduct.getBody()).price())
                    .append(" x ").append(orderProduct.getQuantity()).append(" = ")
                    .append(orderProduct.getSubtotal()).append("</li>");
        }

        message.append("</ul>")
                .append("<p>Total: ").append("$").append(order.getTotal()).append("</p>");

        message.append("""
                                    </td>
                                </tr>
                            </table>
                        
                            <br />
                            <table width="100%" border="0" cellspacing="0" cellpadding="20" bgcolor="#88202b">
                                <tr>
                                    <td align="center" width="100%">
                                        <p style="color: white;">Has recibido este mensaje de parte de The VinylSound</p>
                                    </td>
                                </tr>
                            </table>
                        
                        </body>
                        
                        </html>
                """);

        helper.setFrom(new InternetAddress("The VinylSound <" + emailAdmin + ">"));
        helper.setTo(Objects.requireNonNull(response.getBody()).email());
        helper.setSubject("Nuevo pedido");
        helper.setText(message.toString(), true);

        mailSender.send(mimeMessage);
    }


    public void sendEmailToAdminOrder(Order order, Set<OrderProduct> products) throws NotFoundException, MessagingException {
        var response = userClientFeign.findById(order.getIdUser());
        if (response.getStatusCode().isError()){
            throw new NotFoundException("No se ha encontrado el usuario con el id: " + order.getIdUser());
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());

        StringBuilder message = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="es">
                    
                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Detalle del Pedido</title>
                    <style>
                        body {
                            font-family: "Roboto", sans-serif;
                            margin: 0;
                            padding: 0;
                            background-color: #1e1719;
                            color: #f2eede;
                        }
                    
                        h1, h2, h3 {
                            color: #f2eede;
                        }
                    
                        h2 {
                            font-size: 24px;
                        }
                    
                        h3 {
                            font-size: 20px;
                        }
                    
                        p {
                            font-size: 16px;
                            line-height: 1.5;
                            color: #f2eede;
                        }
                    
                        ul {
                            list-style-type: none;
                            padding: 0;
                            margin: 0;
                        }
                    
                        ul li {
                            font-size: 16px;
                            margin-bottom: 10px;
                            color: #f2eede;
                        }
                    
                        strong {
                            font-weight: bold;
                        }
                    
                        /* Estilo del div principal */
                        .container {
                            max-width: 600px;
                            margin: 0 auto;
                            padding: 20px;
                        }
                    
                        header {
                            background-color: #150d10;
                            padding: 20px 0;
                            text-align: center;
                        }
                    
                        header img {
                            max-width: 100%;
                            height: auto;
                        }
                    
                        footer {
                            text-align: center;
                            margin-top: 20px;
                            background-color: #150d10;
                            color: #f2eede;
                            padding: 10px 0;
                        }
                    
                    </style>
                </head>
                    
                <body style="background-color: #1e1719; color: #f2eede; font-family: "Roboto", sans-serif; margin: 0; padding: 0;">
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#88202b">
                        <tr>
                            <td style="padding: 20px;">
                                <img src="https://res.cloudinary.com/dnb5fwh6o/image/upload/v1711230036/Logo_waig2q.png" alt="Logo" style="width: 4.5rem; display: block;">
                            </td>
                        </tr>
                    </table>
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="20" bgcolor="#1e1719">
                        <tr>
                            <td>
                                <h1 style="color: #f2eede;"><b>Hola!</b></h1>
                                <h2 style="font-size: 24px;">Hemos recibido un pedido:</h2>
                    
                                <hr />
                    
                                <h3 style="font-size: 20px;"><b>Detalle del Pedido:</b></h3>
                                <ul>
                """);

        for (OrderProduct orderProduct : products) {
            var responseProduct = catalogClientFeign.findById(orderProduct.getId().getIdProduct());
            if (responseProduct.getStatusCode().isError()) {
                throw new NotFoundException("No se ha encontrado el producto con el id: " + orderProduct.getId().getIdProduct());
            }
            message
                    .append("<li style=\"font-size: 16px; margin-bottom: 10px;\">")
                    .append(Objects.requireNonNull(responseProduct.getBody()).title())
                    .append("  $");
            message
                    .append(Objects.requireNonNull(responseProduct.getBody()).price())
                    .append(" x ").append(orderProduct.getQuantity()).append(" = ")
                    .append(orderProduct.getSubtotal()).append("</li>");
        }
        message.append("</ul>")
                .append("<p><strong>Total:</strong> ").append("$").append(order.getTotal()).append("</p>");


        message.append("<h3 style=\"font-size: 20px;\"><b>Detalle del Usuario</b></h3><hr />")
                .append("<p><strong>Nombre:</strong> ").append(Objects.requireNonNull(response.getBody()).name()).append(" ")
                .append(response.getBody().lastName()).append("</p>")
                .append("<p><strong>Email:</strong> ").append(response.getBody().email()).append("</p>")
                .append("<p><strong>Ciudad:</strong> ").append(response.getBody().city()).append("</p>")
                .append("<p><strong>Código Postal:</strong> ").append(response.getBody().postalCode()).append("</p>")
                .append("<p><strong>Dirección:</strong> ").append(response.getBody().address()).append("</p><br />");

        message.append("""
                    <table width="100%" border="0" cellspacing="0" cellpadding="00" bgcolor="#88202b">
                        <tr>
                            <td style="padding: 20px;" align="center" width="100%">
                                <p style="color: white;">Has recibido este mensaje de parte de The VinylSound</p>
                            </td>
                        </tr>
                    </table>
                    
                </body>
                    
                </html>
                    
                """);

        helper.setFrom(new InternetAddress("The VinylSound <" + emailAdmin + ">"));
        helper.setTo(emailAdmin);
        helper.setSubject("Nuevo Pedido");
        helper.setText(message.toString(), true);

        mailSender.send(mimeMessage);
    }

}

