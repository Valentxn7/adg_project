module org.example.adg_projet {
    requires javafx.controls;
    requires com.google.gson;  // com.google.code.gson:gson:2.11.0

    opens adg to com.google.gson;
    exports adg;
    exports adg.data;
    opens adg.data to com.google.gson;
    exports adg.vues;
    opens adg.vues to com.google.gson;
    exports adg.control;
    opens adg.control to com.google.gson;

}