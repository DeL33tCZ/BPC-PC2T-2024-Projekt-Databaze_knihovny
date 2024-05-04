# **BPC-PC2T Projekt 2024 (Databáze knihovny v jazyce Java)**
**Autoři: Daniel Linduška (257135), Martin Panáček (257148)**

**Obsah**:
* Úvod
* Použití
* easySearch()
* Zajímavost
  
## Úvod
Tato práce vznikla z důvodu splnění projektu nutného pro získání zápočtu. Zadání bylo primitivní: vytvoření databáze pro knihovnu a implementace nezbytných funkcí pro správu knih v databázi včetně načítání a ukládání SQL databáze. Tato práce splňuje všechny body popsané v zadání projektu.

## Použití
Použití aplikace je jednoduché. Ve svém zvoleném IDE spustíme funkci main v java souboru Aplikace a tím se nám spustí hlavní loop aplikace. Zobrazí se nám list možností správy databáze
* přidání knihy (při vytváření je na výběr román nebo učebnice)
* úprava knihy (easySearch) (upravit se dá autor, rok vydání, stav dostupnosti)
* smazání knihy (easySearch)
* změna stavu dostupnosti knihy (easySearch) (k dispozici, nebo vypůjčeno)
* výpis všech knih abecedně
* vyhledání určité knihy (easySearch)
* vyhledání autora (easySearch)
* vyhledání žánru (easySearch)
* výpis vypůjčených
* uložení knihy do souboru (json - složka knizky)
* načtení knihy ze souboru (json - složka knizky)
* ukončení aplikace (vyčištění tabulky a uložení nových hodnot, se kterými se pracovalo)

## easySearch()
Vlastně vytvořená metoda, která byla přidána z důvodu lenosti zadávání dlouhých názvů knih. Stačí zadat pár písmen a vypíší se všechny vstupy, ve kterých stejná skladba písmen sedí. Při odkliknutí má uživatel na výběr všechny vstupy dané kategorie.

## Zajímavost
V src adresáři se nachází složka inmemmoriam, ve které se nachází začátek našeho řešení, které jsme vytvořili před tím, než jsme znali HashMap objekt.
