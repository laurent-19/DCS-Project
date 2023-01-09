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

public class Intersection_two {
    public static void main(String[] args) {

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Intersection Two";

        pn.NetworkPort = 1082;

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
        // ------------------------------------------ Intersection 2 ----------------------------------------------- //
        // --------------------------------------------------------------------------------------------------------- //

        // ********************************************************************************************************* //
        // ********************************************* Places **************************************************** //
        // ********************************************************************************************************* //

        // ---------------------------------------------- Lane 1 --------------------------------------------------- //
        // ------------------------------------------- Oituz Left In ----------------------------------------------- //

        DataCar p16 = new DataCar();
        p16.SetName("P_a3");
        pn.PlaceList.add(p16);

        DataCarQueue p17 = new DataCarQueue();
        p17.Value.Size = 3;
        p17.SetName("P_x3");
        pn.PlaceList.add(p17);

        DataString p18 = new DataString();
        p18.SetName("P_TL3");
        pn.PlaceList.add(p18);

        DataCar p19 = new DataCar();
        p19.SetName("P_b3");
        pn.PlaceList.add(p19);

        DataTransfer op3 = new DataTransfer();
        op3.SetName("OP3");
        op3.Value = new TransferOperation("localhost", "1083", "in3");
        pn.PlaceList.add(op3);

        // ---------------------------------------------- Lane 2 --------------------------------------------------- //
        // ------------------------------------------ Oituz Right In ----------------------------------------------- //

        DataCar p20 = new DataCar();
        p20.SetName("P_a4");
        pn.PlaceList.add(p20);

        DataCarQueue p21 = new DataCarQueue();
        p21.Value.Size = 3;
        p21.SetName("P_x4");
        pn.PlaceList.add(p21);

        DataString p22 = new DataString();
        p22.SetName("P_TL4");
        pn.PlaceList.add(p22);

        DataCar p23 = new DataCar();
        p23.SetName("P_b4");
        pn.PlaceList.add(p23);

        DataTransfer op4 = new DataTransfer();
        op4.SetName("OP4");
        op4.Value = new TransferOperation("localhost", "1083", "in4");
        pn.PlaceList.add(op4);

        // -------------------------------------------- Exit lane 1 ------------------------------------------------ //
        // --------------------------------------------- Oituz Out ------------------------------------------------ //

        DataCarQueue p24 = new DataCarQueue();
        p24.Value.Size = 3;
        p24.SetName("P_o4");
        pn.PlaceList.add(p24);

        DataCar p25 = new DataCar();
        p25.SetName("P_o4Exit");
        pn.PlaceList.add(p25);

        // -------------------------------------------- Exit lane 2 ------------------------------------------------ //
        // --------------------------------------------- Buia Out - ------------------------------------------------ //

        DataCarQueue p26 = new DataCarQueue();
        p26.Value.Size = 3;
        p26.SetName("P_o5");
        pn.PlaceList.add(p26);

        DataCar p27 = new DataCar();
        p27.SetName("P_o5Exit");
        pn.PlaceList.add(p27);

        // ---------------------------------------------- Center --------------------------------------------------- //

        DataCarQueue p28 = new DataCarQueue();
        p28.Value.Size = 3;
        p28.SetName("P_I2");
        pn.PlaceList.add(p28);

        // ---------------------------------------------- Lane 1 --------------------------------------------------- //
        // ------------------------------------------- Oituz Left In ----------------------------------------------- //

        // T14
        PetriTransition t14 = new PetriTransition(pn);
        t14.TransitionName = "T_u3";
        t14.InputPlaceName.add("P_a3");
        t14.InputPlaceName.add("P_x3");

        Condition T14Ct1 = new Condition(t14, "P_a3", TransitionCondition.NotNull);
        Condition T14Ct2 = new Condition(t14, "P_x3", TransitionCondition.CanAddCars);
        T14Ct1.SetNextCondition(LogicConnector.AND, T14Ct2);

        GuardMapping grdT14 = new GuardMapping();
        grdT14.condition = T14Ct1;
        grdT14.Activations.add(new Activation(t14, "P_a3", TransitionOperation.AddElement, "P_x3"));
        t14.GuardMappingList.add(grdT14);

        Condition T14Ct3 = new Condition(t14, "P_a3", TransitionCondition.NotNull);
        Condition T14Ct4 = new Condition(t14, "P_x3", TransitionCondition.CanNotAddCars);
        T14Ct3.SetNextCondition(LogicConnector.AND, T14Ct4);

        GuardMapping grdT141 = new GuardMapping();
        grdT141.condition = T14Ct3;
        grdT141.Activations.add(new Activation(t14, "full", TransitionOperation.SendOverNetwork, "OP3"));
        grdT141.Activations.add(new Activation(t14, "P_a3", TransitionOperation.Move, "P_a3"));
        t14.GuardMappingList.add(grdT141);

        t14.Delay = 0;
        pn.Transitions.add(t14);

        // T15
        PetriTransition t15 = new PetriTransition(pn);
        t15.TransitionName = "T_e3";
        t15.InputPlaceName.add("P_x3");
        t15.InputPlaceName.add("P_TL3");

        Condition T15Ct1 = new Condition(t15, "P_TL3", TransitionCondition.Equal, "green");
        Condition T15Ct2 = new Condition(t15, "P_x3", TransitionCondition.HaveCar);
        T15Ct1.SetNextCondition(LogicConnector.AND, T15Ct2);

        GuardMapping grdT15 = new GuardMapping();
        grdT15.condition = T15Ct1;
        grdT15.Activations.add(new Activation(t15, "P_x3", TransitionOperation.PopElementWithoutTarget, "P_b3"));
        grdT15.Activations.add(new Activation(t15, "P_TL3", TransitionOperation.Move, "P_TL3"));
        t15.GuardMappingList.add(grdT15);

        t15.Delay = 0;
        pn.Transitions.add(t15);

        // T16
        PetriTransition t16 = new PetriTransition(pn);
        t16.TransitionName = "T_i3";
        t16.InputPlaceName.add("P_b3");
        t16.InputPlaceName.add("P_I2");

        Condition T16Ct1 = new Condition(t16, "P_b3", TransitionCondition.NotNull);
        Condition T16Ct2 = new Condition(t16, "P_I2", TransitionCondition.CanAddCars);
        T16Ct1.SetNextCondition(LogicConnector.AND, T16Ct2);

        GuardMapping grdT16 = new GuardMapping();
        grdT16.condition = T16Ct1;
        grdT16.Activations.add(new Activation(t16, "P_b3", TransitionOperation.AddElement, "P_I2"));
        t16.GuardMappingList.add(grdT16);

        t16.Delay = 0;
        pn.Transitions.add(t16);

        // ---------------------------------------------- Lane 2 --------------------------------------------------- //
        // ------------------------------------------ Oituz Right In ----------------------------------------------- //

        // T17
        PetriTransition t17 = new PetriTransition(pn);
        t17.TransitionName = "T_u4";
        t17.InputPlaceName.add("P_a4");
        t17.InputPlaceName.add("P_x4");

        Condition T17Ct1 = new Condition(t17, "P_a4", TransitionCondition.NotNull);
        Condition T17Ct2 = new Condition(t17, "P_x4", TransitionCondition.CanAddCars);
        T17Ct1.SetNextCondition(LogicConnector.AND, T17Ct2);

        GuardMapping grdT17 = new GuardMapping();
        grdT17.condition = T17Ct1;
        grdT17.Activations.add(new Activation(t17, "P_a4", TransitionOperation.AddElement, "P_x4"));
        t17.GuardMappingList.add(grdT17);

        Condition T17Ct3 = new Condition(t17, "P_a4", TransitionCondition.NotNull);
        Condition T17Ct4 = new Condition(t17, "P_x4", TransitionCondition.CanNotAddCars);
        T17Ct3.SetNextCondition(LogicConnector.AND, T17Ct4);

        GuardMapping grdT171 = new GuardMapping();
        grdT171.condition = T17Ct3;
        grdT171.Activations.add(new Activation(t17, "full", TransitionOperation.SendOverNetwork, "OP4"));
        grdT171.Activations.add(new Activation(t17, "P_a4", TransitionOperation.Move, "P_a4"));
        t17.GuardMappingList.add(grdT171);

        t17.Delay = 0;
        pn.Transitions.add(t17);

        // T18
        PetriTransition t18 = new PetriTransition(pn);
        t18.TransitionName = "T_e4";
        t18.InputPlaceName.add("P_x4");
        t18.InputPlaceName.add("P_TL4");

        Condition T18Ct1 = new Condition(t18, "P_TL4", TransitionCondition.Equal, "green");
        Condition T18Ct2 = new Condition(t18, "P_x4", TransitionCondition.HaveCar);
        T18Ct1.SetNextCondition(LogicConnector.AND, T18Ct2);

        GuardMapping grdT18 = new GuardMapping();
        grdT18.condition = T18Ct1;
        grdT18.Activations.add(new Activation(t18, "P_x4", TransitionOperation.PopElementWithoutTarget, "P_b4"));
        grdT18.Activations.add(new Activation(t18, "P_TL4", TransitionOperation.Move, "P_TL4"));
        t18.GuardMappingList.add(grdT18);

        t18.Delay = 0;
        pn.Transitions.add(t18);

        // T19
        PetriTransition t19 = new PetriTransition(pn);
        t19.TransitionName = "T_i4";
        t19.InputPlaceName.add("P_b4");
        t19.InputPlaceName.add("P_I3");

        Condition T19Ct1 = new Condition(t19, "P_b4", TransitionCondition.NotNull);
        Condition T19Ct2 = new Condition(t19, "P_I3", TransitionCondition.CanAddCars);
        T19Ct1.SetNextCondition(LogicConnector.AND, T19Ct2);

        GuardMapping grdT19 = new GuardMapping();
        grdT19.condition = T19Ct1;
        grdT19.Activations.add(new Activation(t19, "P_b4", TransitionOperation.PopElementWithoutTarget, "P_I2"));
        t19.GuardMappingList.add(grdT19);

        t19.Delay = 0;
        pn.Transitions.add(t19);

        // -------------------------------------------- Exit lane 1 ------------------------------------------------ //
        // --------------------------------------------- Oituz Out ------------------------------------------------ //

        // T20
        PetriTransition t20 = new PetriTransition(pn);
        t20.TransitionName = "T_g4";
        t20.InputPlaceName.add("P_I2");
        t20.InputPlaceName.add("P_o4");

        Condition T20Ct1 = new Condition(t20, "P_I2", TransitionCondition.HaveCarForMe);
        Condition T20Ct2 = new Condition(t20, "P_o4", TransitionCondition.CanAddCars);
        T20Ct1.SetNextCondition(LogicConnector.AND, T20Ct2);

        GuardMapping grdT20 = new GuardMapping();
        grdT20.condition = T20Ct1;
        grdT20.Activations.add(new Activation(t20, "P_I2", TransitionOperation.PopElementWithTargetToQueue, "P_o4"));
        t20.GuardMappingList.add(grdT20);

        t20.Delay = 0;
        pn.Transitions.add(t20);

        // T21
        PetriTransition t21 = new PetriTransition(pn);
        t21.TransitionName = "T_g4Exit";
        t21.InputPlaceName.add("P_o4");

        Condition T21Ct1 = new Condition(t21, "P_o4", TransitionCondition.HaveCar);

        GuardMapping grdT21 = new GuardMapping();
        grdT21.condition = T21Ct1;
        grdT21.Activations.add(new Activation(t21, "P_o4", TransitionOperation.PopElementWithoutTarget, "P_o4Exit"));
        t21.GuardMappingList.add(grdT21);

        t21.Delay = 0;
        pn.Transitions.add(t21);

        // -------------------------------------------- Exit lane 2 ------------------------------------------------ //
        // --------------------------------------------- Buia Out -------------------------------------------------- //

        // T22
        PetriTransition t22 = new PetriTransition(pn);
        t22.TransitionName = "T_g5";
        t22.InputPlaceName.add("P_I2");
        t22.InputPlaceName.add("P_o5");

        Condition T22Ct1 = new Condition(t22, "P_I2", TransitionCondition.HaveCarForMe);
        Condition T22Ct2 = new Condition(t22, "P_o5", TransitionCondition.CanAddCars);
        T22Ct1.SetNextCondition(LogicConnector.AND, T22Ct2);

        GuardMapping grdT22 = new GuardMapping();
        grdT22.condition = T22Ct1;
        grdT22.Activations.add(new Activation(t22, "P_I2", TransitionOperation.PopElementWithTargetToQueue, "P_o5"));
        t22.GuardMappingList.add(grdT22);

        t22.Delay = 0;
        pn.Transitions.add(t22);

        // T23
        PetriTransition t23 = new PetriTransition(pn);
        t23.TransitionName = "T_g4Exit";
        t23.InputPlaceName.add("P_o5");

        Condition T23Ct1 = new Condition(t23, "P_o5", TransitionCondition.HaveCar);

        GuardMapping grdT23 = new GuardMapping();
        grdT23.condition = T23Ct1;
        grdT23.Activations.add(new Activation(t23, "P_o5", TransitionOperation.PopElementWithoutTarget, "P_o5Exit"));
        t23.GuardMappingList.add(grdT23);

        t23.Delay = 0;
        pn.Transitions.add(t23);

        // -------------------------------------------------------------------------------------
        // ----------------------------PNStart-------------------------------------------------
        // -------------------------------------------------------------------------------------

        System.out.println("Lane two started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);
    }
}
