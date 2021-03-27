<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h2>Order details for customer:
                    <xsl:value-of select="Order/CustomerName"/>
                </h2>
                <h3>
                    Order Id: <xsl:value-of select="Order/@id"/>
                </h3>
                <table border="1">
                    <tr>
                        <th>Item Id</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Category</th>
                    </tr>
<!--                    <xsl:for-each select="Order/Item">-->
<!--                        <xsl:sort select="Price" data-type="number" order="descending"/>-->
                        <tr>
                            <td>
                                <xsl:value-of select="ItemId"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="ItemName"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="Price"></xsl:value-of>
                            </td>
                            <td>
                                <xsl:value-of select="Quantity"></xsl:value-of>
                            </td>
                            <xsl:choose>
                                <xsl:when test="Price>=100">
                                    <td><b>Platinum</b></td>
                                </xsl:when>
                                <xsl:when test="Price>=20">
                                    <td><b>Gold</b></td>
                                </xsl:when>
                                <xsl:otherwise>
                                    <td><b>Silver</b></td>
                                </xsl:otherwise>
                            </xsl:choose>
                        </tr>
<!--                    </xsl:for-each>-->
                </table>
            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>