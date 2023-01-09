package UVS;

import Components.*;
import DataObjects.DataString;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Receiver {
    public static void main(String[] args) {

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Receiver";
        pn.NetworkPort = 1081;

        DataString p1 = new DataString();
        p1.SetName("P1");
        p1.SetValue("signal");
        pn.PlaceList.add(p1);

        DataString p2 = new DataString();
        p2.SetName("P2");
        pn.PlaceList.add(p2);

        DataString p3 = new DataString();
        p3.SetName("P3");
        pn.PlaceList.add(p3);

        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "t1";
        t1.InputPlaceName.add("P1");
        t1.InputPlaceName.add("P3");

        Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "P3", TransitionCondition.NotNull);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;
        grdT1.Activations.add(new Activation(t1, "P3", TransitionOperation.Move, "P2"));

        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "t2";
        t2.InputPlaceName.add("P2");

        Condition T2Ct1 = new Condition(t2, "P2", TransitionCondition.NotNull);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "P2", TransitionOperation.Move, "P1"));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        //-------------------------------------------------------------------------------------
        //----------------------------PN Start-------------------------------------------------
        //-------------------------------------------------------------------------------------

        System.out.println("Receiver started \n ------------------------------");
        pn.Delay = 2000;
        //pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}
