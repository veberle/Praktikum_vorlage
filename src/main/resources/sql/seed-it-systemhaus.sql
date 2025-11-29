-- ------------------------------------------------------------
-- Skillmatrix – Beispiel-Testdaten für ein IT-Systemhaus
--
-- Inhalt:
--   - 5 Abteilungen
--   - 10 Skill-Kategorien
--   - 60 Skills (≈6 pro Kategorie)
--   - 50 Mitarbeiter
--   - Zuordnungen Mitarbeiter ↔ Skills mit Level (1–100)
--
-- Nutzung (Beispiele, siehe README):
--   docker compose up -d
--   docker exec -i skillset-mariadb mariadb -uskill_user -psecret skillset < data/seed-it-systemhaus.sql
-- ------------------------------------------------------------

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- Tabellen leeren (und Auto-Increment zurücksetzen)
TRUNCATE TABLE employee_skill;
TRUNCATE TABLE employee;
TRUNCATE TABLE skill;
TRUNCATE TABLE skill_category;
TRUNCATE TABLE department;

-- 5 Abteilungen
INSERT INTO department (name, description) VALUES
  ('Entwicklung', 'Softwareentwicklung und Architektur'),
  ('IT-Support', 'First/Second-Level Support, Managed Services'),
  ('Consulting', 'Beratung, Anforderungs- und Prozessanalyse'),
  ('DevOps & Operations', 'Betrieb, Automatisierung, CI/CD'),
  ('Vertrieb', 'Sales, Presales, Angebotswesen');

-- 10 Kategorien
INSERT INTO skill_category (name, description) VALUES
  ('Backend', 'Server-seitige Entwicklung und Frameworks'),
  ('Frontend', 'UI-Technologien und Web-Frameworks'),
  ('DevOps', 'Build, Deployment, Automatisierung'),
  ('Cloud', 'Public Cloud Plattformen und Services'),
  ('Datenbanken', 'Relationale & NoSQL Datenbanken'),
  ('Netzwerke', 'Netzwerkgrundlagen und -betrieb'),
  ('Security', 'Sichere Entwicklung und Betrieb'),
  ('Tools', 'Werkzeuge und Plattformen im Alltag'),
  ('Soft Skills', 'Überfachliche Kompetenzen'),
  ('ITSM & Service', 'Service-Management Praktiken');

-- 60 Skills (6 pro Kategorie)
-- Hinweis: IDs beginnen bei 1 entsprechend Einfüge-Reihenfolge
-- Backend (category_id = 1)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('Java', 'Java SE/EE, moderne Sprachfeatures', 1),
  ('Spring Boot', 'Spring Ökosystem, REST, Data JPA', 1),
  ('REST APIs', 'Design, Dokumentation, HTTP', 1),
  ('Node.js', 'JavaScript/TypeScript Backend', 1),
  ('.NET Core', 'C#/.NET, Web APIs', 1),
  ('Python', 'Scripting & Services', 1);

-- Frontend (category_id = 2)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('HTML/CSS', 'Moderne Semantik und Layouts', 2),
  ('JavaScript', 'ES6+, DOM, Fetch', 2),
  ('TypeScript', 'Typsystem, Tooling', 2),
  ('React', 'Komponenten, Hooks', 2),
  ('Angular', 'RxJS, CLI, Module', 2),
  ('Vue.js', 'Composition API', 2);

-- DevOps (category_id = 3)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('Git', 'Branches, Code Review, Workflows', 3),
  ('CI/CD', 'Build & Deploy Pipelines', 3),
  ('Docker', 'Container Images & Runtime', 3),
  ('Kubernetes', 'Orchestrierung, Workloads', 3),
  ('Helm', 'K8s Packaging', 3),
  ('Terraform', 'IaC, Provider', 3);

-- Cloud (category_id = 4)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('AWS', 'Compute, Storage, Networking', 4),
  ('Azure', 'PaaS, IaaS, Identity', 4),
  ('GCP', 'GCE, GKE, IAM', 4),
  ('Cloud Networking', 'VPC/VNet, Routing', 4),
  ('Cloud Security', 'IAM, KMS, Policies', 4),
  ('Serverless', 'Lambda/Functions', 4);

-- Datenbanken (category_id = 5)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('MySQL', 'Relationale DB, SQL', 5),
  ('MariaDB', 'Relationale DB, SQL', 5),
  ('PostgreSQL', 'Relationale DB, SQL', 5),
  ('Oracle', 'Enterprise RDBMS', 5),
  ('MongoDB', 'Dokumentenorientiert', 5),
  ('Redis', 'Key-Value, Caching', 5);

-- Netzwerke (category_id = 6)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('TCP/IP', 'OSI, Subnetting, Routing', 6),
  ('VLAN', 'Layer 2 Segmentierung', 6),
  ('Firewalls', 'Stateful Inspection, NAT', 6),
  ('VPN', 'Site-to-Site, Remote', 6),
  ('DNS/DHCP', 'Namensauflösung, Adressvergabe', 6),
  ('WLAN', '802.11, Roaming, Security', 6);

-- Security (category_id = 7)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('PenTesting', 'Schwachstellenanalyse', 7),
  ('SIEM', 'Log-Korrelation, Alarme', 7),
  ('IAM', 'Identitäten & Zugriffe', 7),
  ('Secure Coding', 'OWASP, Code-Reviews', 7),
  ('TLS/PKI', 'Zertifikate, Trust', 7),
  ('Backup/Restore', 'Strategien, Recovery', 7);

-- Tools (category_id = 8)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('Jira', 'Issue Tracking, Boards', 8),
  ('Confluence', 'Dokumentation, Wiki', 8),
  ('IntelliJ IDEA', 'Java IDE', 8),
  ('VS Code', 'Editor, Extensions', 8),
  ('Linux', 'Shell, Admin, Services', 8),
  ('Windows Server', 'AD, Fileservices', 8);

-- Soft Skills (category_id = 9)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('Kommunikation', 'Zielgruppengerecht, klar', 9),
  ('Teamarbeit', 'Zusammenarbeit & Feedback', 9),
  ('Präsentation', 'Storytelling, Visualisierung', 9),
  ('Projektmanagement', 'Planung, Steuerung', 9),
  ('Kundenorientierung', 'Service-Mindset', 9),
  ('Zeitmanagement', 'Priorisierung, Fokus', 9);

-- ITSM & Service (category_id = 10)
INSERT INTO skill (name, description, skill_category_id) VALUES
  ('ITIL', 'Service Management Framework', 10),
  ('Incident Management', 'Störungshandling', 10),
  ('Problem Management', 'Root Cause, Lessons Learned', 10),
  ('Change Management', 'Risiko, Freigaben', 10),
  ('Asset Management', 'Inventar, Lifecycle', 10),
  ('Monitoring', 'Metriken, Alarme', 10);

-- 50 Mitarbeiter (E-Mails neutralisiert)
-- Verteilung je 10 Mitarbeiter auf die 5 Abteilungen (department_id 1..5)
INSERT INTO employee (first_name, last_name, email, department_id, is_active) VALUES
  ('Max', 'Müller', 'emp01@example.com', 1, TRUE),
  ('Anna', 'Schmidt', 'emp02@example.com', 1, TRUE),
  ('Paul', 'Fischer', 'emp03@example.com', 1, TRUE),
  ('Laura', 'Weber', 'emp04@example.com', 1, TRUE),
  ('Lukas', 'Wagner', 'emp05@example.com', 1, TRUE),
  ('Sofia', 'Becker', 'emp06@example.com', 1, TRUE),
  ('Jonas', 'Hoffmann', 'emp07@example.com', 1, TRUE),
  ('Emilia', 'Schulz', 'emp08@example.com', 1, TRUE),
  ('Leon', 'Koch', 'emp09@example.com', 1, TRUE),
  ('Mia', 'Richter', 'emp10@example.com', 1, TRUE),

  ('Noah', 'Klein', 'emp11@example.com', 2, TRUE),
  ('Lina', 'Wolf', 'emp12@example.com', 2, TRUE),
  ('Ben', 'Schröder', 'emp13@example.com', 2, TRUE),
  ('Emma', 'Neumann', 'emp14@example.com', 2, TRUE),
  ('Felix', 'Schwarz', 'emp15@example.com', 2, TRUE),
  ('Lea', 'Zimmermann', 'emp16@example.com', 2, TRUE),
  ('Elias', 'Braun', 'emp17@example.com', 2, TRUE),
  ('Nele', 'Krüger', 'emp18@example.com', 2, TRUE),
  ('David', 'Hofmann', 'emp19@example.com', 2, TRUE),
  ('Marie', 'Hartmann', 'emp20@example.com', 2, TRUE),

  ('Tim', 'Lange', 'emp21@example.com', 3, TRUE),
  ('Johanna', 'Werner', 'emp22@example.com', 3, TRUE),
  ('Niklas', 'Schmitt', 'emp23@example.com', 3, TRUE),
  ('Clara', 'Kaiser', 'emp24@example.com', 3, TRUE),
  ('Julian', 'Vogel', 'emp25@example.com', 3, TRUE),
  ('Lara', 'Friedrich', 'emp26@example.com', 3, TRUE),
  ('Moritz', 'Günther', 'emp27@example.com', 3, TRUE),
  ('Sarah', 'Weiß', 'emp28@example.com', 3, TRUE),
  ('Tom', 'Keller', 'emp29@example.com', 3, TRUE),
  ('Nina', 'Jäger', 'emp30@example.com', 3, TRUE),

  ('Jan', 'Peters', 'emp31@example.com', 4, TRUE),
  ('Lisa', 'Fuchs', 'emp32@example.com', 4, TRUE),
  ('Daniel', 'Bergmann', 'emp33@example.com', 4, TRUE),
  ('Pia', 'Frank', 'emp34@example.com', 4, TRUE),
  ('Tobias', 'Busch', 'emp35@example.com', 4, TRUE),
  ('Jana', 'Albrecht', 'emp36@example.com', 4, TRUE),
  ('Fabian', 'Kühn', 'emp37@example.com', 4, TRUE),
  ('Eva', 'Horn', 'emp38@example.com', 4, TRUE),
  ('Kevin', 'Sauer', 'emp39@example.com', 4, TRUE),
  ('Katharina', 'Arnold', 'emp40@example.com', 4, TRUE),

  ('Sebastian', 'Lenz', 'emp41@example.com', 5, TRUE),
  ('Amelie', 'Pohl', 'emp42@example.com', 5, TRUE),
  ('Patrick', 'Voigt', 'emp43@example.com', 5, TRUE),
  ('Sophie', 'Brandt', 'emp44@example.com', 5, TRUE),
  ('Alexander', 'Hahn', 'emp45@example.com', 5, TRUE),
  ('Theresa', 'Otto', 'emp46@example.com', 5, TRUE),
  ('Christian', 'Sommer', 'emp47@example.com', 5, TRUE),
  ('Hannah', 'Blum', 'emp48@example.com', 5, TRUE),
  ('Marco', 'Scholz', 'emp49@example.com', 5, TRUE),
  ('Julia', 'Engel', 'emp50@example.com', 5, TRUE);

-- Zuordnungen Mitarbeiter ↔ Skills mit pseudozufälligen Leveln (1–100)
-- Auswahl: ca. 1/8 der Kombinationen, um ~375 Einträge zu erzeugen
INSERT INTO employee_skill (employee_id, skill_id, level, last_update)
SELECT e.id AS employee_id,
       s.id AS skill_id,
       1 + ((e.id * 17 + s.id * 11) % 100) AS level,
       NOW() AS last_update
FROM employee e
JOIN skill s
WHERE ((e.id * 3 + s.id) % 8) = 0;

SET FOREIGN_KEY_CHECKS = 1;

-- Ende der Seed-Daten
