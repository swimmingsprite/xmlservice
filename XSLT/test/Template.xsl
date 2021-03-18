<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <title>Faktúra</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <div class="root">
                    <div class="upper-panel">
                        <div class="left upanel-child bottom-border">
                            <div class="bottom-border">
                                <xsl:if test="/Document/GeneralType = 'Tax document'">
                                    <p>
                                        <b>Daňový doklad</b>
                                    </p>
                                </xsl:if>
                                <xsl:if test="/Document/Type = 'Invoice'">
                                    <h3 class="low-margin">Faktúra č.
                                        <xsl:value-of select="/Document/Number"/>
                                    </h3>
                                </xsl:if>
                            </div>
                            <div class="dates">
                                <xsl:apply-templates select="Document/Dates/*"/>
                                <br/>
                            </div>

                            <xsl:apply-templates select="/Document/Bank"/>

                        </div>


                        <div class="right upanel-child">

                            <xsl:apply-templates select="Document/Supplier"/>
                            <xsl:apply-templates select="Document/Customer"/>
                        </div>
                    </div>

                    <div class="middle-panel">
                        <p>Fakturujem Vám za služby v mesiaci Apríl 2020 podľa príloh
                            <br/>
                            <br/>
                            <span>
                                <b>čiastku 4825,85 EUR</b>
                            </span>
                        </p>
                    </div>

                    <div class="bottom_panel">
                        <div class="bottom-left">
                            <div class="tax-payer bottom-border">
                                <p>Platca DPH</p>
                            </div>
                            <div class="bottom-border">
                                <p>Faktúru vystavil: Zamestnanec</p>
                            </div>
                            <div class="signature">
                                <p>Pečiatka a podpis:</p>
                            </div>


                        </div>


                        <div class="bottom-right">
                            <div class="bottom-border">
                                <p>
                                    <b>Základ bez dph:
                                        <span class="date-span">4000 EUR</span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>DPH 20%:
                                        <span class="date-span">825,85 EUR</span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>Cena vrátane DPH:
                                        <span class="date-span">4825,85 EUR</span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>Čiastka k úhrade:
                                        <span class="date-span">4825,85 EUR</span>
                                    </b>
                                </p>
                                <br/>
                                <br/>
                            </div>
                        </div>
                    </div>

                </div>


            </body>
        </html>

    </xsl:template>

    <xsl:template match="Document/Dates/*">
        <xsl:if test="name(.) = 'Delivery'">
            <p>
                <span>Dátum dodania služby:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Invoice'">
            <p>
                <span>Dátum vystavenia:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Due'">
            <p>
                <span>Dátum splatnosti:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>
    </xsl:template>

    <xsl:template match="/Document/Bank">
        <div>
            <p>Forma úhrady:
                <xsl:if test="./Form = 'Bank Transfer'">
                    Bankový prevod
                </xsl:if>
            </p>
            <p>KS:
                <xsl:value-of select="./KS"/>
            </p>
            <p>VS:
                <xsl:value-of select="./VS"/>
            </p>
            <br/>
        </div>
        <p>
            <b>Banka:
                <xsl:value-of select="./BankName"/>
            </b>
        </p>
        <p>
            <b>Číslo účtu:
                <xsl:value-of select="./IBAN"/>
            </b>
        </p>
    </xsl:template>

    <xsl:template match="/Document/Supplier">
        <div class="name">
            <b>Dodávateľ:</b>
        </div>
        <div class="bottom-border">
            <p class="sup-address">
                <xsl:value-of select="./CompanyName"/>
                <br/>
                <xsl:value-of select="./Address/Street"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="./Address/Number"/>
                <br/>
                <xsl:value-of select="./Address/Zip"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="./Address/City"/>
            </p>
            <br/>
            <p>
                <b>IČ DPH:
                    <xsl:value-of select="./ICDPH"/>
                </b>
            </p>
            <p>
                <b>DIČ:
                    <xsl:value-of select="./DIC"/>
                </b>
            </p>
        </div>
    </xsl:template>

    <xsl:template match="/Document/Customer">
        <div class="name">
            <b>Odberateľ:</b>
        </div>
        <div class="bottom-border">
            <br/>
            <p>
                <b>Odberateľ s.r.o.</b>
            </p>
            <p>Nejaká ulica 12</p>
            <p>
                <b>965 04 Trenčín</b>
            </p>
            <br/>
        </div>
        <div class="bottom-border">
            <p>
                <b>IČO : 98725845 IČ DPH :SK 2098725845</b>
            </p>
        </div>
        <div class="bottom-border">
            <p>
                <b>DIČ : 2098725845</b>
            </p>
        </div>
    </xsl:template>


</xsl:stylesheet>