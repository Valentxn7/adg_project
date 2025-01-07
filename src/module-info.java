module org.example.adg_project {
    requires javafx.controls;
    requires com.google.gson;
    requires java.desktop;

    opens adg to com.google.gson;
    exports adg;
    exports adg.control;
    exports adg.vues;
    exports adg.data;
    opens adg.data to com.google.gson;
    opens adg.vues to com.google.gson;
    opens adg.control to com.google.gson;
}
