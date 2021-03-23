<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">

        <xsl:variable name="tax">
            <xsl:value-of select="((/Document/Price/Base div 100 * /Document/Price/TaxRate) *100 div 100)"/>
        </xsl:variable>

        <xsl:variable name="total">
            <xsl:value-of select="/Document/Price/Base + $tax"/>
        </xsl:variable>

        <xsl:variable name="curr">
            <xsl:value-of select="/Document/Price/Currency"/>
        </xsl:variable>

        <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <title>Faktura</title>
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
                        <p>Fakturuji Vám za služby v měsíci
                            <xsl:apply-templates select="/Document/MonthAndYear"/>
                            podle příloh
                            <br/>
                            <br/>
                            <span>
                                <b>částku
                                    <xsl:value-of select="translate($total, '.', ',')"/>
                                    &#160;<xsl:value-of select="$curr"/>
                                </b>
                            </span>
                        </p>
                    </div>

                    <div class="bottom_panel">
                        <div class="bottom-left">
                            <div class="tax-payer bottom-border">
                                <xsl:choose>
                                    <xsl:when test="/Document/IsTaxPayer">
                                        <p>Platce DPH</p>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <p>Nejsme plátci DPH</p>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                            <div class="bottom-border">
                                <p>Fakturu vystavil:
                                    <xsl:value-of select="/Document/GeneratedBy"/>
                                </p>
                            </div>
                            <div class="signature">
                                <p>Razítko a podpis:</p>
                            </div>
                        </div>


                        <div class="bottom-right">
                            <div class="bottom-border">
                                <p>
                                    <b>Základ bez dph:
                                        <span class="date-span">
                                            <xsl:value-of select="
                                        translate(concat(/Document/Price/Base, ' ', $curr), '.', ',')"/>
                                        </span>
                                    </b>
                                </p>
                                <br/>
                            </div>


                            <div class="bottom-border">
                                <p>
                                    <b>DPH 20%:
                                        <span class="date-span">
                                            <xsl:value-of select="translate(concat(
                                            $tax
                                            , ' ', $curr), '.', ',')"/>
                                        </span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>Cena včetně DPH:
                                        <span class="date-span">
                                            <xsl:value-of select="
                                        translate(concat($total, ' ', $curr), '.', ',')"/>
                                        </span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>Částka k úhradě:
                                        <span class="date-span">
                                            <xsl:value-of select="
                                        translate(concat($total, ' ', $curr), '.', ',')"/>
                                        </span>
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
                <span>Datum dodání služby:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Invoice'">
            <p>
                <span>Datum vystavení:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Due'">
            <p>
                <span>Datum splatnosti:</span>
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
                    Bankovní převod
                </xsl:if>
            </p>
            <p>KS:
                <xsl:value-of select="./KS"/>
            </p>
            <p>VS:
                <xsl:value-of select="../Number"/>
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
            <b>Dodavatel:</b>
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
            <b>Odběratel:</b>
        </div>
        <div class="bottom-border">
            <br/>
            <p>
                <b>
                    <xsl:value-of select="./CompanyName"/>
                </b>
            </p>
            <p>
                <xsl:value-of select="./Address/Street"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="./Address/Number"/>
            </p>
            <p>
                <b>
                    <xsl:value-of select="./Address/Zip"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="./Address/City"/>
                </b>
            </p>
            <br/>
        </div>
        <div class="bottom-border">
            <p>
                <b>IČO:
                    <xsl:value-of select="./ICO"/>
                    <xsl:text>&#160;&#160;&#160;</xsl:text>
                    IČ DPH:
                    <xsl:value-of select="./ICDPH"/>
                </b>
            </p>
        </div>
        <div class="bottom-border">
            <p>
                <b>DIČ:
                    <xsl:value-of select="./DIC"/>
                </b>
            </p>
        </div>
    </xsl:template>

    <xsl:template match="/Document/MonthAndYear">
        <xsl:variable name="subst">
            <xsl:value-of select="substring-before(/Document/MonthAndYear, '/')"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$subst = '1'">
                Leden
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '2'">
                Únor
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '3'">
                Březen
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '4'">
                Duben
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '5'">
                Květen
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '6'">
                Červen
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '7'">
                Červenec
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '8'">
                Srpen
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '9'">
                Září
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '10'">
                Říjen
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '11'">
                Listopad
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '12'">
                Prosinec
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>


</xsl:stylesheet>