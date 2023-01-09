package UVS;

import Components.*;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Round_About {
    public static void main(String[] args) {
        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Round About";
        pn.NetworkPort = 1082;

        DataCar p1 = new DataCar();
        p1.SetName("P1");
        pn.PlaceList.add(p1);

        DataCar p5 = new DataCar();
        p5.SetName("P5");
        pn.PlaceList.add(p5);

        DataCar p6 = new DataCar();
        p6.SetName("P6");
        pn.PlaceList.add(p6);

        DataCar p7 = new DataCar();
        p7.SetName("P7");
        pn.PlaceList.add(p7);

        DataCar p8 = new DataCar();
        p8.SetName("P8");
        pn.PlaceList.add(p8);

        DataCarQueue p2 = new DataCarQueue();
        p2.Value.Size = 3;
        p2.SetName("P2");
        pn.PlaceList.add(p2);

        DataCarQueue p3 = new DataCarQueue();
        p3.Value.Size = 3;
        p3.SetName("P3");
        pn.PlaceList.add(p3);

        DataCarQueue p4 = new DataCarQueue();
        p4.Value.Size = 3;
        p4.SetName("P4");
        pn.PlaceList.add(p4);

        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T1";
        t1.InputPlaceName.add("P1");

        Condition T1Ct1 = new Condition(t1, "P1", TransitionCondition.NotNull);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;
        grdT1.Activations.add(new Activation(t1, "P1", TransitionOperation.AddElement, "P2"));
        t1.GuardMappingList.add(grdT1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T2";
        t2.InputPlaceName.add("P2");

        Condition T2Ct1 = new Condition(t1, "P2", TransitionCondition.HaveCar);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "P2", TransitionOperation.PopElementWithTargetToQueue, "P3"));
        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // T3 ------------------------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T3";
        t3.InputPlaceName.add("P3");

        Condition T3Ct1 = new Condition(t3, "P3", TransitionCondition.HaveCarForMe);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;
        grdT3.Activations.add(new Activation(t3, "P3", TransitionOperation.PopElementWithTargetToQueue, "P4"));
        t3.GuardMappingList.add(grdT3);

        t3.Delay = 0;
        pn.Transitions.add(t3);

        // T4 ------------------------------------------------
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "T4";
        t4.InputPlaceName.add("P4");

        Condition T4Ct1 = new Condition(t4, "P4", TransitionCondition.HaveCarForMe);

        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = T4Ct1;
        grdT4.Activations.add(new Activation(t4, "P4", TransitionOperation.PopElementWithTargetToQueue, "P2"));
        t4.GuardMappingList.add(grdT4);

        t4.Delay = 0;
        pn.Transitions.add(t4);

        // Texit1 ------------------------------------------------
        PetriTransition texit1 = new PetriTransition(pn);
        texit1.TransitionName = "texit1";
        texit1.InputPlaceName.add("P2");

        Condition Text1Ct1 = new Condition(texit1, "P2", TransitionCondition.HaveCarForMe);

        GuardMapping grdText1 = new GuardMapping();
        grdText1.condition = Text1Ct1;
        grdText1.Activations.add(new Activation(texit1, "P2", TransitionOperation.PopElementWithoutTarget, "P6"));
        texit1.GuardMappingList.add(grdText1);

        texit1.Delay = 0;
        pn.Transitions.add(texit1);

        // Texit2 ------------------------------------------------
        PetriTransition texit2 = new PetriTransition(pn);
        texit2.TransitionName = "texit2";
        texit2.InputPlaceName.add("P3");

        Condition Text2Ct1 = new Condition(texit2, "P3", TransitionCondition.HaveCarForMe);

        GuardMapping grdText2 = new GuardMapping();
        grdText2.condition = Text2Ct1;
        grdText2.Activations.add(new Activation(texit2, "P3", TransitionOperation.PopElementWithoutTarget, "P7"));
        texit2.GuardMappingList.add(grdText2);

        texit2.Delay = 0;
        pn.Transitions.add(texit2);

        // Texit3 ------------------------------------------------
        PetriTransition texit3 = new PetriTransition(pn);
        texit3.TransitionName = "texit3";
        texit3.InputPlaceName.add("P4");

        Condition Text3Ct1 = new Condition(texit3, "P4", TransitionCondition.HaveCarForMe);

        GuardMapping grdText3 = new GuardMapping();
        grdText3.condition = Text3Ct1;
        grdText3.Activations.add(new Activation(texit3, "P4", TransitionOperation.PopElementWithoutTarget, "P8"));
        texit3.GuardMappingList.add(grdText3);

        texit3.Delay = 0;
        pn.Transitions.add(texit3);

        // Texit4 ------------------------------------------------
        PetriTransition texit4 = new PetriTransition(pn);
        texit4.TransitionName = "texit4";
        texit4.InputPlaceName.add("P2");

        Condition Text4Ct1 = new Condition(texit4, "P2", TransitionCondition.HaveCarForMe);

        GuardMapping grdText4 = new GuardMapping();
        grdText4.condition = Text4Ct1;
        grdText4.Activations.add(new Activation(texit4, "P2", TransitionOperation.PopElementWithoutTarget, "P5"));
        texit4.GuardMappingList.add(grdText4);

        texit4.Delay = 0;
        pn.Transitions.add(texit4);

        // -------------------------------------------------------------------------------------
        // ----------------------------PNStart-------------------------------------------------
        // -------------------------------------------------------------------------------------

        System.out.println("Round About started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}
