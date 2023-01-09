package Project;

import Components.*;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Intersection_one {
    public static void main(String[] args) {

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Intersection One";

        pn.NetworkPort = 1080;

        DataString full = new DataString();
        full.SetName("full");
        full.SetValue("full");
        pn.ConstantPlaceList.add(full);

        DataString green = new DataString();
        green.Printable = false;
        green.SetName("green");
        green.SetValue("green");
        pn.ConstantPlaceList.add(green);

        // --------------------------------------------------------------------------------------------------------- //
        // ------------------------------------------ Intersection 1 ----------------------------------------------- //
        // --------------------------------------------------------------------------------------------------------- //

        // ********************************************************************************************************* //
        // ********************************************* Places **************************************************** //
        // ********************************************************************************************************* //

        // ---------------------------------------------- Lane 1 --------------------------------------------------- //
        // --------------------------------------------- Oituz In  ------------------------------------------------- //

        DataCar p1 = new DataCar();
        p1.SetName("P_a1");
        pn.PlaceList.add(p1);

        DataCarQueue p2 = new DataCarQueue();
        p2.Value.Size = 3;
        p2.SetName("P_x1");
        pn.PlaceList.add(p2);

        DataString p3 = new DataString();
        p3.SetName("P_TL1");
        pn.PlaceList.add(p3);

        DataCar p4 = new DataCar();
        p4.SetName("P_b1");
        pn.PlaceList.add(p4);

        DataTransfer op1 = new DataTransfer();
        op1.SetName("OP1");
        op1.Value = new TransferOperation("localhost", "1081", "in1");
        pn.PlaceList.add(op1);

        // ---------------------------------------------- Lane 2 --------------------------------------------------- //
        // --------------------------------------------- Miraslau In ----------------------------------------------- //

        DataCar p5 = new DataCar();
        p5.SetName("P_a2");
        pn.PlaceList.add(p5);

        DataCarQueue p6 = new DataCarQueue();
        p6.Value.Size = 3;
        p6.SetName("P_x2");
        pn.PlaceList.add(p6);

        DataString p7 = new DataString();
        p7.SetName("P_TL2");
        pn.PlaceList.add(p7);

        DataCar p8 = new DataCar();
        p8.SetName("P_b2");
        pn.PlaceList.add(p8);

        DataTransfer op2 = new DataTransfer();
        op2.SetName("OP2");
        op2.Value = new TransferOperation("localhost", "1081", "in2");
        pn.PlaceList.add(op2);

        // -------------------------------------------- Exit lane 1 ------------------------------------------------ //
        // ------------------------------------------ Oituz Out Short ---------------------------------------------- //

        DataCarQueue p9 = new DataCarQueue();
        p9.Value.Size = 3;
        p9.SetName("P_o1");
        pn.PlaceList.add(p9);

        DataCar p10 = new DataCar();
        p10.SetName("P_o1Exit");
        pn.PlaceList.add(p10);

        // -------------------------------------------- Exit lane 2 ------------------------------------------------ //
        // -------------------------------------------- Miraslau Out ----------------------------------------------- //

        DataCarQueue p11 = new DataCarQueue();
        p11.Value.Size = 3;
        p11.SetName("P_o2");
        pn.PlaceList.add(p11);

        DataCar p12 = new DataCar();
        p12.SetName("P_o2Exit");
        pn.PlaceList.add(p12);

        // -------------------------------------------- Exit lane 3 ------------------------------------------------ //
        // ------------------------------------------ Oituz Out Long ---------------------------------------------- //

        DataCarQueue p13 = new DataCarQueue();
        p13.Value.Size = 3;
        p13.SetName("P_o3");
        pn.PlaceList.add(p13);

        DataCar p14 = new DataCar();
        p14.SetName("P_o3Exit");
        pn.PlaceList.add(p14);

        DataTransfer p_O3_OUT = new DataTransfer();
        p_O3_OUT.SetName("p_O3_OUT");
        p_O3_OUT.Value = new TransferOperation("localhost", "1082", "P_a3");
        pn.PlaceList.add(p_O3_OUT);

        // ---------------------------------------------- Center --------------------------------------------------- //

        DataCarQueue p15 = new DataCarQueue();
        p15.Value.Size = 3;
        p15.SetName("P_I1");
        pn.PlaceList.add(p15);


        // ********************************************************************************************************* //
        // ******************************************* Transitions ************************************************* //
        // ********************************************************************************************************* //


        // ---------------------------------------------- Lane 1 --------------------------------------------------- //

        // T1
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T_u1";
        t1.InputPlaceName.add("P_a1");
        t1.InputPlaceName.add("P_x1");

        Condition T1Ct1 = new Condition(t1, "P_a1", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "P_x1", TransitionCondition.CanAddCars);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;
        grdT1.Activations.add(new Activation(t1, "P_a1", TransitionOperation.AddElement, "P_x1"));
        t1.GuardMappingList.add(grdT1);

        Condition T1Ct3 = new Condition(t1, "P_a1", TransitionCondition.NotNull);
        Condition T1Ct4 = new Condition(t1, "P_x1", TransitionCondition.CanNotAddCars);
        T1Ct3.SetNextCondition(LogicConnector.AND, T1Ct4);

        GuardMapping grdT1_1 = new GuardMapping();
        grdT1_1.condition = T1Ct3;
        grdT1_1.Activations.add(new Activation(t1, "full", TransitionOperation.SendOverNetwork, "OP1"));
        grdT1_1.Activations.add(new Activation(t1, "P_a1", TransitionOperation.Move, "P_a1"));
        t1.GuardMappingList.add(grdT1_1);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // T2
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T_e1";
        t2.InputPlaceName.add("P_x1");
        t2.InputPlaceName.add("P_TL1");

        Condition T2Ct1 = new Condition(t2, "P_TL1", TransitionCondition.Equal, "green");
        Condition T2Ct2 = new Condition(t2, "P_x1", TransitionCondition.HaveCar);
        T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition = T2Ct1;
        grdT2.Activations.add(new Activation(t2, "P_x1", TransitionOperation.PopElementWithoutTarget, "P_b1"));
        grdT2.Activations.add(new Activation(t2, "P_TL1", TransitionOperation.Move, "P_TL1"));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // T3
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T_i1";
        t3.InputPlaceName.add("P_b1");
        t3.InputPlaceName.add("P_I1");

        Condition T3Ct1 = new Condition(t3, "P_b1", TransitionCondition.NotNull);
        Condition T3Ct2 = new Condition(t3, "P_I1", TransitionCondition.CanAddCars);
        T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;
        grdT3.Activations.add(new Activation(t3, "P_b1", TransitionOperation.AddElement, "P_I1"));
        t3.GuardMappingList.add(grdT3);

        t3.Delay = 0;
        pn.Transitions.add(t3);

        // ---------------------------------------------- Lane 2 --------------------------------------------------- //

        // T4
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "T_u2";
        t4.InputPlaceName.add("P_a2");
        t4.InputPlaceName.add("P_x2");

        Condition T4Ct1 = new Condition(t4, "P_a2", TransitionCondition.NotNull);
        Condition T4Ct2 = new Condition(t4, "P_x2", TransitionCondition.CanAddCars);
        T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);

        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = T4Ct1;
        grdT4.Activations.add(new Activation(t4, "P_a2", TransitionOperation.AddElement, "P_x2"));
        t4.GuardMappingList.add(grdT4);

        Condition T4Ct3 = new Condition(t4, "P_a2", TransitionCondition.NotNull);
        Condition T4Ct4 = new Condition(t4, "P_x2", TransitionCondition.CanNotAddCars);
        T4Ct3.SetNextCondition(LogicConnector.AND, T4Ct4);

        GuardMapping grdT41 = new GuardMapping();
        grdT41.condition = T4Ct3;
        grdT41.Activations.add(new Activation(t4, "full", TransitionOperation.SendOverNetwork, "OP2"));
        grdT41.Activations.add(new Activation(t4, "P_a2", TransitionOperation.Move, "P_a2"));
        t4.GuardMappingList.add(grdT41);

        t4.Delay = 0;
        pn.Transitions.add(t4);

        // T5
        PetriTransition t5 = new PetriTransition(pn);
        t5.TransitionName = "T_e2";
        t5.InputPlaceName.add("P_x2");
        t5.InputPlaceName.add("P_TL2");

        Condition T5Ct1 = new Condition(t5, "P_TL2", TransitionCondition.Equal, "green");
        Condition T5Ct2 = new Condition(t5, "P_x2", TransitionCondition.HaveCar);
        T5Ct1.SetNextCondition(LogicConnector.AND, T5Ct2);

        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition = T5Ct1;
        grdT5.Activations.add(new Activation(t5, "P_x2", TransitionOperation.PopElementWithoutTarget, "P_b2"));
        grdT5.Activations.add(new Activation(t5, "P_TL2", TransitionOperation.Move, "P_TL2"));
        t5.GuardMappingList.add(grdT2);

        t5.Delay = 0;
        pn.Transitions.add(t5);

        // T6
        PetriTransition t6 = new PetriTransition(pn);
        t6.TransitionName = "T_i2";
        t6.InputPlaceName.add("P_b2");
        t6.InputPlaceName.add("P_I1");

        Condition T6Ct1 = new Condition(t6, "P_b2", TransitionCondition.NotNull);
        Condition T6Ct2 = new Condition(t6, "P_I1", TransitionCondition.CanAddCars);
        T6Ct1.SetNextCondition(LogicConnector.AND, T6Ct2);

        GuardMapping grdT6 = new GuardMapping();
        grdT6.condition = T6Ct1;
        grdT6.Activations.add(new Activation(t6, "P_b2", TransitionOperation.AddElement, "P_I1"));
        t6.GuardMappingList.add(grdT6);

        t6.Delay = 0;
        pn.Transitions.add(t6);

        // -------------------------------------------- Exit lane 1 ------------------------------------------------ //

        // T7
        PetriTransition t7 = new PetriTransition(pn);
        t7.TransitionName = "T_g1";
        t7.InputPlaceName.add("P_I1");
        t7.InputPlaceName.add("P_o1");

        Condition T7Ct1 = new Condition(t7, "P_I1", TransitionCondition.HaveCarForMe);
        Condition T7Ct2 = new Condition(t7, "P_o1", TransitionCondition.CanAddCars);
        T7Ct1.SetNextCondition(LogicConnector.AND, T7Ct2);

        GuardMapping grdT7 = new GuardMapping();
        grdT7.condition = T7Ct1;
        grdT7.Activations.add(new Activation(t7, "P_I1", TransitionOperation.PopElementWithTargetToQueue, "P_o1"));
        t7.GuardMappingList.add(grdT7);

        t7.Delay = 0;
        pn.Transitions.add(t7);

        // T8
        PetriTransition t8 = new PetriTransition(pn);
        t8.TransitionName = "T_g1Exit";
        t8.InputPlaceName.add("P_o1");

        Condition T8Ct1 = new Condition(t8, "P_o1", TransitionCondition.HaveCar);

        GuardMapping grdT8 = new GuardMapping();
        grdT8.condition = T8Ct1;
        grdT8.Activations.add(new Activation(t8, "P_o1", TransitionOperation.PopElementWithoutTarget, "P_o1Exit"));
        t8.GuardMappingList.add(grdT8);

        t8.Delay = 0;
        pn.Transitions.add(t8);

        // -------------------------------------------- Exit lane 2 ------------------------------------------------ //

        // T9
        PetriTransition t9 = new PetriTransition(pn);
        t9.TransitionName = "T_g2";
        t9.InputPlaceName.add("P_I1");
        t9.InputPlaceName.add("P_o2");

        Condition T9Ct1 = new Condition(t9, "P_I1", TransitionCondition.HaveCarForMe);
        Condition T9Ct2 = new Condition(t9, "P_o2", TransitionCondition.CanAddCars);
        T9Ct1.SetNextCondition(LogicConnector.AND, T9Ct2);

        GuardMapping grdT9 = new GuardMapping();
        grdT9.condition = T9Ct1;
        grdT9.Activations.add(new Activation(t9, "P_I1", TransitionOperation.PopElementWithTargetToQueue, "P_o2"));
        t9.GuardMappingList.add(grdT9);

        t9.Delay = 0;
        pn.Transitions.add(t9);

        // T10
        PetriTransition t10 = new PetriTransition(pn);
        t10.TransitionName = "T_g2Exit";
        t10.InputPlaceName.add("P_o2");

        Condition T10Ct1 = new Condition(t10, "P_o2", TransitionCondition.HaveCar);

        GuardMapping grdT10 = new GuardMapping();
        grdT10.condition = T10Ct1;
        grdT10.Activations.add(new Activation(t10, "P_o2", TransitionOperation.PopElementWithoutTarget, "P_o2Exit"));
        t10.GuardMappingList.add(grdT10);

        t10.Delay = 0;
        pn.Transitions.add(t10);

        // -------------------------------------------- Exit lane 3 ------------------------------------------------ //

        // T11
        PetriTransition t11 = new PetriTransition(pn);
        t11.TransitionName = "T_g3";
        t11.InputPlaceName.add("P_I1");
        t11.InputPlaceName.add("P_o3");

        Condition T11Ct1 = new Condition(t11, "P_I1", TransitionCondition.HaveCarForMe);
        Condition T11Ct2 = new Condition(t11, "P_o3", TransitionCondition.CanAddCars);
        T11Ct1.SetNextCondition(LogicConnector.AND, T11Ct2);

        GuardMapping grdT11 = new GuardMapping();
        grdT11.condition = T11Ct1;
        grdT11.Activations.add(new Activation(t11, "P_I1", TransitionOperation.PopElementWithTargetToQueue, "P_o3"));
        t11.GuardMappingList.add(grdT11);

        t11.Delay = 0;
        pn.Transitions.add(t11);

        // T12
        PetriTransition t12 = new PetriTransition(pn);
        t12.TransitionName = "T_g3Exit";
        t12.InputPlaceName.add("P_o3");

        Condition T12Ct1 = new Condition(t12, "P_o3", TransitionCondition.HaveCar);

        GuardMapping grdT12 = new GuardMapping();
        grdT12.condition = T12Ct1;
        grdT12.Activations.add(new Activation(t12, "P_o3", TransitionOperation.PopElementWithoutTarget, "P_o3Exit"));
        t12.GuardMappingList.add(grdT12);

        t12.Delay = 0;
        pn.Transitions.add(t12);

        // ---------------------------------------------- Linkage -------------------------------------------------- //

        // T13 - links intersection 1 to intersection 2

        PetriTransition t13 = new PetriTransition(pn);
        t13.TransitionName = "T_link";
        t13.InputPlaceName.add("P_o3Exit");

        Condition T13Ct1 = new Condition(t13, "P_o3Exit", TransitionCondition.NotNull);

        GuardMapping grdT13 = new GuardMapping();
        grdT13.condition = T13Ct1;
        grdT13.Activations.add(new Activation(t13, "P_o3Exit", TransitionOperation.SendOverNetwork, "p_O3_OUT"));
        t13.GuardMappingList.add(grdT13);

        t13.Delay = 0;
        pn.Transitions.add(t13);

        // -------------------------------------------------------------------------------------
        // ----------------------------PNStart-------------------------------------------------
        // -------------------------------------------------------------------------------------

        System.out.println("Lane one started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}

