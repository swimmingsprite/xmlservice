<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:iv="http://www.example.com/Invoice"
>
    <xsl:output method="html"/>

    <xsl:template match="/">

        <xsl:variable name="base">
            <xsl:value-of select="round(iv:Document/iv:Price/iv:Base * 100) div 100"/>
        </xsl:variable>

        <xsl:variable name="tax">
            <xsl:value-of select="(round(($base div 100 * number(iv:Document/iv:Price/iv:TaxRate)) *100) div 100)"/>
        </xsl:variable>

        <xsl:variable name="total">
            <xsl:value-of select="round((iv:Document/iv:Price/iv:Total)*100) div 100"/>
        </xsl:variable>

        <xsl:variable name="curr">
            <xsl:value-of select="iv:Document/iv:Price/iv:Currency"/>
        </xsl:variable>

        <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <title>Invoice</title>
                <!--                <link rel="stylesheet" type="text/css" href="style.css"/>-->
                <style>
                    body, html {
                    box-sizing: border-box;
                    position: relative;
                    }

                    .root {
                    border: 2px solid black;
                    margin: 2% 2%;
                    position: relative;
                    overflow: hidden;
                    max-width: 850px;
                    min-width: 478px;
                    }
                    .low-margin {
                    margin-bottom: 0;
                    margin-top: 5px;
                    }

                    .upper-panel {
                    position: relative;
                    display: block;
                    width: 100%;
                    }

                    .left {
                    display: inline-block;
                    border-right: 1px solid black;
                    }

                    .right {
                    position: absolute;
                    display: inline-block;
                    border-left: 1px solid black;
                    }

                    .upanel-child {
                    width: 49.9%;
                    }

                    .name {
                    top: 0;
                    border-bottom: 2px solid black;
                    padding-left: 5px;
                    }

                    .bottom-border {
                    border-bottom: 2px solid black;
                    }

                    .bottom-border h3 {
                    padding-left: 5px;
                    }

                    p {
                    margin: 0;
                    padding-left: 5px;
                    }

                    .date-span {
                    right: 5%;
                    position: absolute;
                    }

                    .dates {
                    position: relative;
                    }

                    .sup-address {
                    text-align: center;
                    }

                    .middle-panel {
                    margin-top: 100px;
                    margin-bottom: 200px;
                    position: relative;
                    display: block;
                    width: 100%;
                    }

                    .middle-panel p {
                    text-align: center;
                    font-size: 1.1rem;
                    }

                    .tax-payer {
                    margin-top: 5px;
                    }

                    .bottom-right {
                    position: absolute;
                    display: inline-block;
                    border-left: 1px solid black;
                    width: 49.9%;
                    }

                    .bottom-left {
                    position: relative;
                    /*float: left;*/
                    display: inline-block;
                    border-right: 1px solid black;
                    width: 49.9%;
                    }

                    .bottom-left p {
                    margin: 0;
                    }

                    .bottom_panel {
                    display: block;
                    position: relative;
                    width: 100%;
                    }

                    .signature {
                    padding-top: 90px;
                    }

                    .bottom-right:nth-of-type(2) {
                    border-top: 2px solid black;
                    }
                </style>
            </head>
            <body>
                <div class="root">
                    <div class="upper-panel">
                        <div class="left upanel-child bottom-border">
                            <div class="bottom-border">
                                <p>
                                    <b>
                                        <xsl:value-of select="/iv:Document/iv:GeneralType"/>
                                    </b>
                                </p>
                                <h3 class="low-margin">
                                    <xsl:value-of select="/iv:Document/iv:Type"/> num.:
                                    <xsl:value-of select="/iv:Document/iv:Number"/>
                                </h3>
                            </div>
                            <div class="dates">
                                <xsl:apply-templates select="iv:Document/iv:Dates/*"/>
                                <br/>
                            </div>
                            <xsl:apply-templates select="iv:Document/iv:Bank"/>
                        </div>

                        <div class="right upanel-child">
                            <xsl:apply-templates select="iv:Document/iv:Supplier"/>
                            <xsl:apply-templates select="iv:Document/iv:Customer"/>
                        </div>
                    </div>

                    <div class="middle-panel">
                        <p>In the month of
                            <xsl:apply-templates select="iv:Document/iv:MonthAndYear"/>
                            , we will invoice you
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
                                    <xsl:when test="iv:Document/iv:IsTaxPayer">
                                        <p>VAT payer</p>                                    </xsl:when>
                                    <xsl:otherwise>
                                        <p>The supply is exempt from VAT.</p>
                                    </xsl:otherwise>
                                </xsl:choose>
                            </div>
                            <div class="bottom-border">
                                <p>Issued by:
                                    <xsl:value-of select="iv:Document/iv:GeneratedBy"/>
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
                                        translate(concat($base, ' ', $curr), '.', ',')"/>
                                        </span>
                                    </b>
                                </p>
                                <br/>
                            </div>


                            <div class="bottom-border">
                                <p>
                                    <b>VAT <xsl:value-of select="iv:Document/iv:Price/iv:TaxRate"/>%:
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

    <xsl:template match="iv:Document/iv:Dates/*">
        <xsl:if test="name(.) = 'Delivery'">
            <p>
                <span>Delivery date:</span>
                <span class="date-span">
                    <xsl:value-of select="./iv:Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Invoice'">
            <p>
                <span>Invoice date:</span>
                <span class="date-span">
                    <xsl:value-of select="./iv:Date"/>
                </span>
            </p>
        </xsl:if>

        <xsl:if test="name(.) = 'Due'">
            <p>
                <span>Payment due date:</span>
                <span class="date-span">
                    <xsl:value-of select="./iv:Date"/>
                </span>
            </p>
        </xsl:if>
    </xsl:template>

    <xsl:template match="iv:Document/iv:Bank">
        <div>
            <p>Payment form: <xsl:value-of select="./iv:Form"/></p>
            <p>KS:
                <xsl:value-of select="./iv:KS"/>
            </p>
            <p>VS:
                <xsl:value-of select="../iv:Number"/>
            </p>
            <br/>
        </div>
        <p>
            <b>Bank name:
                <xsl:value-of select="./iv:BankName"/>
            </b>
        </p>
        <p>
            <b>IBAN:
                <xsl:value-of select="./iv:IBAN"/>
            </b>
        </p>
    </xsl:template>

    <xsl:template match="iv:Document/iv:Supplier">
        <div class="name">
            <b>Supplier:</b>
        </div>
        <div class="bottom-border">
            <p class="sup-address">
                <xsl:value-of select="./iv:CompanyName"/>
                <br/>
                <xsl:value-of select="./iv:Address/iv:Street"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="./iv:Address/iv:Number"/>
                <br/>
                <xsl:value-of select="./iv:Address/iv:Zip"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="./iv:Address/iv:City"/>
            </p>
            <br/>
            <p>
                <b>VAT ID:
                    <xsl:value-of select="./iv:ICDPH"/>
                </b>
            </p>
            <p>
                <b>TAX ID:
                    <xsl:value-of select="./iv:DIC"/>
                </b>
            </p>
        </div>
    </xsl:template>

    <xsl:template match="iv:Document/iv:Customer">
        <div class="name">
            <b>Buyer:</b>
        </div>
        <div class="bottom-border">
            <br/>
            <p>
                <b>
                    <xsl:value-of select="./iv:CompanyName"/>
                </b>
            </p>
            <p>
                <xsl:value-of select="./iv:Address/iv:Street"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="./iv:Address/iv:Number"/>
            </p>
            <p>
                <b>
                    <xsl:value-of select="./iv:Address/iv:Zip"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="./iv:Address/iv:City"/>
                </b>
            </p>
            <br/>
        </div>
        <div class="bottom-border">
            <p>
                <b>ID:
                    <xsl:value-of select="./iv:ICO"/>
                    <xsl:text>&#160;&#160;&#160;</xsl:text>
                    VAT ID:
                    <xsl:value-of select="./iv:ICDPH"/>
                </b>
            </p>
        </div>
        <div class="bottom-border">
            <p>
                <b>TAX ID:
                    <xsl:value-of select="./iv:DIC"/>
                </b>
            </p>
        </div>
    </xsl:template>

    <xsl:template match="iv:MonthAndYear">
        <xsl:variable name="subst">
            <xsl:value-of select="substring-before(. , '/')"/>
        </xsl:variable>
        <xsl:choose>
            <xsl:when test="$subst = '1'">
                January
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '2'">
                February
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '3'">
                March
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '4'">
                April
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '5'">
                May
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '6'">
                June
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '7'">
                July
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '8'">
                August
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '9'">
                September
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '10'">
                October
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '11'">
                November
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
            <xsl:when test="$subst = '12'">
                December
                <xsl:value-of select="substring-after(., '/')"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>


</xsl:stylesheet>