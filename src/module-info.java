module org.example.adg_project {
    requires javafx.controls;
    requires com.google.gson;

    opens adg to com.google.gson;
    exports adg;
    exports adg.data;
    opens adg.data to com.google.gson;
    exports adg.vues;
    opens adg.vues to com.google.gson;
    exports adg.control;
    opens adg.control to com.google.gson;

}