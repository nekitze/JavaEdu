package edu.school21.annotations.forms;

import edu.school21.annotations.core.HtmlForm;
import edu.school21.annotations.core.HtmlInput;

@HtmlForm(fileName = "order_form.html", action = "/orders", method = "post")
public class OrderForm {
    @HtmlInput(type = "text", name = "product_name", placeholder = "Enter Product Name")
    private String productName;

    @HtmlInput(type = "number", name = "count", placeholder = "Enter Count Of Products")
    private int count;
}
