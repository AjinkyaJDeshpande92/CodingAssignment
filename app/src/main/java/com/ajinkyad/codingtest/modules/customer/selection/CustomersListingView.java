package com.ajinkyad.codingtest.modules.customer.selection;


import com.ajinkyad.codingtest.entities.CustomerDetailsResponse;
import com.ajinkyad.codingtest.modules.common.CommonView;

import java.util.ArrayList;

interface CustomersListingView extends CommonView{

    void renderCustomersList(ArrayList<CustomerDetailsResponse> customersList);
}
