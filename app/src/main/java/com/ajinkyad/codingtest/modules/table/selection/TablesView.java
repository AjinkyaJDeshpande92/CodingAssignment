package com.ajinkyad.codingtest.modules.table.selection;


import com.ajinkyad.codingtest.entities.TableDetailsResponse;
import com.ajinkyad.codingtest.modules.common.CommonView;

import java.util.ArrayList;

interface TablesView extends CommonView{

    void renderTablesList(ArrayList<TableDetailsResponse> tables);

    void reservationAddedConfirmation();
}
