# XML service

Táto aplikácia umožňuje nasledujúce operácie s vybranými XML dokumentami:
* validáciu XML s XSD súborom
* transformovanie XML na objekt a jeho uloženie do databázy
* transformovanie XML na HTML
* poslať transformovaný dokument na e-mail
* transformovať XML na JSON

### Spustenie
___
**Windows**
```
start mvnw spring-boot:run
```

### Validácia
___
**POST request:**
* **ContentType**: application/xml
* **Parameters**: 
* **Body**: XML as String
* **Path**: http://localhost:8080/validate
* **Returns:** HTTP 200/HTTP 400/HTTP 500, Body: null or error message

### Transformácia XML do JSON
___
**POST request:**
* **ContentType**: application/xml
* **Parameters**: 
* **Body**: XML as String
* **Path**: http://localhost:8080/toJSON
* **Returns:** HTTP 200/HTTP 400, Body: JSON

### Transformácia do HTML
___
**POST request:**
* **ContentType**: application/xml
* **Parameters**: variant (String) 
* **Body**: XML as String
* **Path**: http://localhost:8080/transformToHtml?variant=SK
* **Returns:** HTTP 200/HTTP 500, Body: HTML as String

### Transformácia do HTML a odoslanie na email
___
> U niektorých mailbox providerov neprechádzaju e-maily. 

**POST request:**
* **ContentType**: application/xml
* **Parameters**: variant (String), email (String) 
* **Body**: XML as String
* **Path**: http://localhost:8080/transformToHtml?variant=SK&email=myemail@example.com
* **Returns:** HTTP 200/HTTP 500 

### Uloženie do databázy
___
**POST request:**
* **ContentType**: application/xml
* **Parameters**:  
* **Body**: XML as String
* **Path**: http://localhost:8080/save
* **Returns:** HTTP 200/HTTP 500

### XML Sample
```
<?xml version="1.0" encoding="utf-8" standalone="yes"?>
<Document
        xmlns="http://www.example.com/Invoice"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.example.com/Invoice">
        
    <GeneralType>Tax document</GeneralType>
    <Type>Invoice</Type>
    <Number>123456</Number>
    <MonthAndYear>4/2020</MonthAndYear>
    <IsTaxPayer>true</IsTaxPayer>
    <GeneratedBy>Zamestnanec</GeneratedBy>

    <Dates>
        <Delivery>
            <Title>Delivery Date</Title>
            <Date>21.10.2020</Date>
        </Delivery>

        <Invoice>
            <Title>Invoice Date</Title>
            <Date>21.10.2020</Date>
        </Invoice>

        <Due>
            <Title>Due Date</Title>
            <Date>21.10.2020</Date>
        </Due>
    </Dates>

    <Bank>
        <Form>Bank Transfer</Form>
        <KS>308</KS>
        <VS>12345</VS>
        <BankName>Tatra Banka</BankName>
        <IBAN>SK45 1100 0000 0026 1234 5678</IBAN>
    </Bank>

    <Supplier>
        <CompanyName>Firma s.r.o.</CompanyName>
        <Address>
            <Street>Uličná</Street>
            <Number>12/7654</Number>
            <Zip>083 12</Zip>
            <City>Bratislava</City>
        </Address>
        <ICDPH>SK2045123456</ICDPH>
        <DIC>2045123456</DIC>
    </Supplier>

    <Customer>
        <CompanyName>Odberateľ s.r.o.</CompanyName>
        <Address>
            <Street>Nejaká ulica</Street>
            <Number>12</Number>
            <Zip>965 04</Zip>
            <City>Trenčín</City>
        </Address>
        <ICDPH>SK2098725845</ICDPH>
        <DIC>2098725845</DIC>
        <ICO>98725845</ICO>
    </Customer>

    <Price>
        <Base>4225.30</Base>
        <TaxRate>20</TaxRate>
        <Currency>EUR</Currency>
        <Total>5070.36</Total>
        <ToPay>5070.36</ToPay>
    </Price>
</Document>
```


