module BSTs_and_Inventory_Lab {
    requires gson;
    requires java.sql;

    opens org.headroyce.lross2024 to gson;
    exports org.headroyce.lross2024;
}