# Tornado - užduočių valdymo sistema

## Sprendžiamo uždavinio aprašymas

### Sistemos paskirtis

Projekto tikslas – sukurti užduočių valdymo įrankį, kuris leistų prisijungusiems sistemos naudotojams efektyviai
planuoti, vykdyti ir stebėti savo užduotis. Sistema leis valdyti projektus, sekcijas, užduotis ir naudotojų paskyras.

Aukščiausiame hierarchijos lygyje yra projektai, kurie suskirstomi į sekcijas, o sekcijose kuriamos užduotys. Projektai
grupuoja platesnius tikslus, sekcijos leidžia smulkiau suskirstyti veiklas pagal temas ar etapus, o užduotys yra
konkretūs darbai, kuriuos reikia atlikti.

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

Projektas ⇒ Sekcija ⇒ Užduotis

- Projektas (project)
- Sekcija (section)
- Užduotis (task)
- Naudotojas (user)

## Sistemos architektūra

### Sistemos sudedamosios dalys:

- Kliento pusė (angl. Front-end) – bus realizuojama naudojant `SvelteKit` karkasą. SvelteKit - tai Svelte paremtas
  karkasas, kuris remiasi Svelte pagrindais ir suteikia galingų funkcijų, tokių kaip SSR, kodo skaidymas, failų
  maršrutizavimas, kurios palengvina sudėtingų programų kūrimą.
- Serverio pusė (angl. Back-end) – bus realizuojama naudojant `Ktor` karkasą. Ktor yra asinchroninis karkasas, skirtas
  mikroservisams, žiniatinklio programoms ir kt. kurti, naudojant Kotlin programavimo kalbą.
- Duomenys bus saugomi `PostgreSQL` duomenų bazėje, kuri bus pasiekiama per `Exposed ORM`. Duomenų bazė yra reliacinė,
  kuri leidžia saugoti duomenis lentelėse bei sudaryti ryšius tarp jų.

## API metodai (Endpoints)

### Projektai (Projects)

1. `GET /api/v1/projects` – gauti visus projektų sąrašą
2. `GET /api/v1/projects/{id}` – gauti vieną projektą pagal ID
3. `POST /api/v1/projects` – sukurti naują projektą
4. `PATCH /api/v1/projects/{id}` – atnaujinti projektą pagal ID
5. `DELETE /api/v1/projects/{id}` – ištrinti projektą pagal ID

### Sekcijos (Sections)

1. `GET /api/v1/sections` – gauti vieną sekciją pagal ID
2. `GET /api/v1/sections/{id}` – gauti vieną sekciją pagal ID
3. `POST /api/v1/projects/{id}/sections` - sukurti naują sekciją
4. `PATCH /api/v1/sections/{id}` – atnaujinti sekciją pagal ID
5. `DELETE /api/v1/sections/{id}` – ištrinti sekciją pagal ID

### Užduotys (Tasks)

1. `GET /api/v1/tasks` – gauti visų užduočių sąrašą
2. `GET /api/v1/tasks/{id}` – gauti vieną užduotį pagal ID
3. `GET /api/v1/projects/{projectId}/sections/{sectionId}/tasks` – gauti visų užduočių sąrašą pagal projekto ir sekcijos ID
4. `POST /api/v1/sections/{id}/tasks` – sukurti naują užduotį
5. `PATCH /api/v1/tasks/{id}` – atnaujinti užduotį pagal ID
6. `DELETE /api/v1/tasks/{id}` – ištrinti užduotį pagal ID
