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
                <title>Faktúra</title>
                <link rel="stylesheet" type="text/css" href="style.css"/>
            </head>
            <body>
                <div class="root">
                    <div class="upper-panel">
                        <div class="left upanel-child bottom-border">
                            <div class="bottom-border">
                                <p>
                                    <b>
                                        <xsl:value-of select="/Document/GeneralType"/>
                                    </b>
                                </p>

                                <h3 class="low-margin">
                                    <xsl:value-of select="/Document/Type"/> num.:
                                     <xsl:value-of select="/Document/Number"/>
                                    </h3>
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
                        <p>In the month of
                            <xsl:apply-templates select="/Document/MonthAndYear"/>, we will invoice you
                            <br/>
                            <br/>
                            <span>
                                <b>for a total of
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
                                        <p>VAT payer</p>
                                    </xsl:when>
                                    <xsl:otherwise>
                                        <p>The supply is exempt from VAT.</p>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                            <div class="bottom-border">
                                <p>Issued by:
                                    <xsl:value-of select="/Document/GeneratedBy"/>
                                </p>
                            </div>
                            <div class="signature">
                                <p>Stamp and signature:</p>
                            </div>
                        </div>


                        <div class="bottom-right">
                            <div class="bottom-border">
                                <p>
                                    <b>Base without VAT:
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
                                    <b>VAT 20%:
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
                                    <b>Total with VAT:
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
                                    <b>To pay:
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
                <span>Delivery date:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Invoice'">
            <p>
                <span>Invoice date:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Due'">
            <p>
                <span>Payment due date:</span>
                <span class="date-span">
                    <xsl:value-of select="./Date"/>
                </span>
            </p>
        </xsl:if>
    </xsl:template>

    <xsl:template match="/Document/Bank">
        <div>
            <p>Payment form: <xsl:value-of select="./Form"/></p>
            <p>KS:
                <xsl:value-of select="./KS"/>
            </p>
            <p>VS:
                <xsl:value-of select="../Number"/>
            </p>
            <br/>
        </div>
        <p>
            <b>Bank name:
                <xsl:value-of select="./BankName"/>
            </b>
        </p>
        <p>
            <b>IBAN:
                <xsl:value-of select="./IBAN"/>
            </b>
        </p>
    </xsl:template>

    <xsl:template match="/Document/Supplier">
        <div class="name">
            <b>Supplier:</b>
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
                <b>VAT ID:
                    <xsl:value-of select="./ICDPH"/>
                </b>
            </p>
            <p>
                <b>TAX ID:
                    <xsl:value-of select="./DIC"/>
                </b>
            </p>
        </div>
    </xsl:template>

    <xsl:template match="/Document/Customer">
        <div class="name">
            <b>Buyer:</b>
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
                <b>ID:
                    <xsl:value-of select="./ICO"/>
                    <xsl:text>&#160;&#160;&#160;</xsl:text>
                    VAT ID:
                    <xsl:value-of select="./ICDPH"/>
                </b>
            </p>
        </div>
        <div class="bottom-border">
            <p>
                <b>TAX ID:
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
                January
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '2'">
                February
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '3'">
                March
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '4'">
                April
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '5'">
                May
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '6'">
                June
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '7'">
                July
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '8'">
                August
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '9'">
                September
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '10'">
                October
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '11'">
                November
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '12'">
                December
                <xsl:value-of select="substring-after(/Document/MonthAndYear, '/')"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>


</xsl:stylesheet>