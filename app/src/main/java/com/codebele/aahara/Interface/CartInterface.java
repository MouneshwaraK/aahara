package com.codebele.aahara.Interface;

import com.codebele.aahara.Models.Models.ViewCartPojo;

public interface CartInterface {
    void removeTotal();

    void updateCart(int pos, ViewCartPojo.Items viewcart, String cartCount);
}
