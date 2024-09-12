# Tornado - užduočių valdymo sistema

## Sprendžiamo uždavinio aprašymas

### Sistemos paskirtis

Projekto tikslas – sukurti užduočių valdymo įrankį, kuris leistų prisijungusiems sistemos naudotojams efektyviai planuoti, vykdyti ir stebėti savo užduotis. Sistema leis valdyti projektus, sekcijas, užduotis ir naudotojų paskyras.

Aukščiausiame hierarchijos lygyje yra projektai, kurie suskirstomi į sekcijas, o sekcijose kuriamos užduotys. Projektai grupuoja platesnius tikslus, sekcijos leidžia smulkiau suskirstyti veiklas pagal temas ar etapus, o užduotys yra konkretūs darbai, kuriuos reikia atlikti.

Sistemai naudotojai bus trijų rolių: svečias, registruotas naudotojas ir administratorius.

### Funkciniai reikalavimai

#### Svečias galės:

1. Peržiūrėti prisijungimo puslapį
2. Peržiūrėti registracijos puslapį
3. Prisijungti prie sistemos
4. Užsiregistruoti į sistemą

<a></a>

#### Registruotas sistemos naudotojas galės:

1. Prisijungti prie sistemos
2. Atsijungti nuo sistemos
3. Sukurti, ištrinti, redaguoti, peržiūrėti projektus
4. Sukurti, ištrinti, redaguoti, peržiūrėti sekcijas
5. Sukurti, ištrinti, redaguoti, peržiūrėti užduotis

<a></a>

#### Administratorius galės:

1. Prisijungti prie sistemos
2. Atsijungti nuo sistemos
3. Valdyti naudotojus

## Taikomosios srities objektai

- Projektas (project)
- Sekcija (section)
- Užduotis (task)
- Naudotojas (user)

## Sistemos architektūra

### Sistemos sudedamosios dalys:

- Kliento pusė (angl. Front-end) – bus realizuojama naudojant SvelteKit karkasą. SvelteKit - tai Svelte paremtas karkasas, kuris remiasi Svelte pagrindais ir suteikia galingų funkcijų, tokių kaip SSR, kodo skaidymas, failų maršrutizavimas, kurios palengvina sudėtingų programų kūrimą.
- Serverio pusė (angl. Back-end) – bus realizuojama naudojant Ktor karkasą. Ktor yra asinchroninis karkasas, skirtas mikroservisams, žiniatinklio programoms ir kt. kurti, naudojant Kotlin programavimo kalbą.
- Duomenys bus saugomi PostgreSQL duomenų bazėje, kuri bus pasiekiama per Exposed ORM. Duomenų bazė yra reliacinė, kuri leidžia saugoti duomenis lentelėse bei sudaryti ryšius tarp jų.

## API metodai (Endpoints)

### Projektas

1. `GET /projects` – gauti visus projektų sąrašą
2. `GET /projects/{id}` – gauti vieną projektą pagal ID
3. `POST /projects` – sukurti naują projektą
4. `PUT /projects/{id}` – atnaujinti projektą pagal ID
5. `DELETE /projects/{id}` – ištrinti projektą pagal ID

### Sekcija

1. `GET /sections` – gauti vieną sekciją pagal ID
2. `GET /sections/{id}` – gauti vieną sekciją pagal ID
3. `POST /sections` – sukurti naują sekciją
4. `PUT /sections/{id}` – atnaujinti sekciją pagal ID
5. `DELETE /sections/{id}` – ištrinti sekciją pagal ID
6. `GET /projects/{projectId}/sections` – gauti visų sekcijų sąrašą pagal projekto ID

### Užduotis

1. `GET /tasks` – gauti visų užduočių sąrašą
2. `GET /tasks/{id}` – gauti vieną užduotį pagal ID
3. `POST /tasks` – sukurti naują užduotį
4. `PUT /tasks/{id}` – atnaujinti užduotį pagal ID
5. `DELETE /tasks/{id}` – ištrinti užduotį pagal ID
6. `GET /sections/{sectionId}/tasks` – gauti visų užduočių sąrašą pagal sekcijos ID
7. `GET /projects/{projectId}/tasks` – gauti visų užduočių sąrašą pagal projekto ID

### Naudotojas

1. `GET /users` – gauti visų naudotojų sąrašą
2. `GET /users/{id}` – gauti vieną naudotoją pagal ID
3. `POST /users` – sukurti naują naudotoją
4. `PUT /users/{id}` – atnaujinti naudotoją pagal ID
5. `DELETE /users/{id}` – ištrinti naudotoją pagal ID
