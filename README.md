# JSP-Servlets

Über diesen Link die Datei "32-bit/64-bit Windows Service Installer" herunterladen und ausführen um tomcat zu installieren.
https://tomcat.apache.org/download-90.cgi

Alternativ ist hier der Link aus dem Kurs zu JSP, Servlets und JDBC:
https://iks-gmbh.udemy.com/course/jsp-tutorial/learn/lecture/4009516#overview

Am Ende der Installation kann man Tomcat direk starten. Wenn man Tomcat auch nach dem Abschließen der Installation starten möchte, dann muss man die Datei im Installationsordner von Tomcat/bin/tomcat9.exe(nicht tomcat9w.exe) ausführen.
Es erscheint die Windows Konsole und Tomcat startet. Will man Tomcat wieder beenden, so drückt man die Tastenkombination strg + c und der Server fährt wieder herunter.

Die Datei JSP_Servlets muss in den Installationsordener von Tomcat/bin/webapps verschoben werden, dann wartet man kurz, nach kurzer Zeit erscheint ein Ordner mit dem Namen JSP_Servlets

Über diesen Link läd man die webversion vom MySQL herunter und installiert sie anschließend.
https://dev.mysql.com/downloads/windows/installer/8.0.html

Alternativ ist hier der udemy Link aus dem Kurs zu JSP, Servlets und JDBC:
https://iks-gmbh.udemy.com/course/jsp-tutorial/learn/lecture/4616792#overview

Jetzt öffnet man MySQL und fügt eine Datenbank hinzu. Hierzu macht man einen Rechtsklick in das linke Feld mit der Überschrift 'Schemas' und wählt die option 'create schema' aus. Es önnet sich ein neuer Tab. In das Feld Name schreibt man jsp_test(es ist wichtig, dass der Name genauso geschrieben ist) und klickt auf das x des Tabs welcher zuvor aufgegangen ist. Das Programm frag, ob man speichern möchte und man speichert.
Nun klinkt man oben links in der Ecke auf "File" und dann auf "Open SQL Sript" oder man betätigt alternativ die Tastenkombination STR+Umschalt+o. In dem frisch aufgegangenen Fenster wählt man nun die Datei meetings.sql aus und öffnet sie. Anschließend führt man sie durch einen klick auf den gelben Blitz aus und die Tabelle wurde erstellt.
Um dies zu überprüfen macht man nun einen Rechtsklick in das Fenster SCHEMAS links am Bildschirmrand und klickt auf "Refresh All". In dem Order jsp_test/Tables müsste sich nun eine Tabelle meetings befinden. Durch einen Rechtsklick und einen anschließenden klick auf "Select Rows - Limit 1000" kann man sich die Tabelle anzeigen lassen.

Jetzt öffnet man diesen Link:
http://localhost:8080/JSP_Servlets/
und schon läuft das Programm.


Es funktioniert in der Version übrigens nur der Adminlogin.
Benutzername: admin
Passwort: admin
