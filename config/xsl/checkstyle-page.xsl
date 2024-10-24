<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html" indent="yes"/>
    <xsl:decimal-format decimal-separator="." grouping-separator="," />

    <xsl:key name="files" match="file" use="@name" />

    <xsl:template match="checkstyle">
        <html>
            <head>
                <style type="text/css">
                    .bannercell {
                    border: 0px;
                    padding: 0px;
                    }
                    body {
                    margin-left: 10;
                    margin-right: 10;
                    font:normal 80% arial,helvetica,sanserif;
                    background-color:#FFFFFF;
                    color:#000000;
                    }
                    .a td {
                    background: #efefef;
                    }
                    .b td {
                    background: #fff;
                    }
                    th, td {
                    text-align: left;
                    vertical-align: top;
                    }
                    th {
                    font-weight:bold;
                    background: #ccc;
                    color: black;
                    }
                    table, th, td {
                    font-size:100%;
                    border: none;
                    }
                    h2 {
                    font-weight:bold;
                    font-size:140%;
                    margin-bottom: 5;
                    }
                    h3 {
                    font-size:100%;
                    font-weight:bold;
                    background: #525D76;
                    color: white;
                    text-decoration: none;
                    padding: 5px;
                    margin-right: 2px;
                    margin-left: 2px;
                    margin-bottom: 0;
                    }
                    .error-column {
                    width: 50%;
                    }
                    .line-column {
                    width: 50%;
                    }
                    .file-column {
                    width: 50%;
                    }
                    .summary-errors {
                    width: 50%;
                    }
                </style>
            </head>
            <body>
                <a name="top"></a>
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td class="bannercell" rowspan="2">
                        </td>
                        <td class="text-align:right"><h2>CheckStyle Audit</h2></td>
                    </tr>
                </table>
                <hr size="1"/>

                <xsl:apply-templates select="." mode="summary"/>
                <hr size="1" width="100%" align="left"/>

                <xsl:apply-templates select="." mode="filelist"/>
                <hr size="1" width="100%" align="left"/>

                <xsl:apply-templates select="file[@name and count(error) > 0 and generate-id(.) = generate-id(key('files', @name))]" />

                <hr size="1" width="100%" align="left"/>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="checkstyle" mode="filelist">
        <h3>Files</h3>
        <table class="log" border="0" cellpadding="5" cellspacing="2" width="100%">
            <tr>
                <th class="file-column">Name</th>
                <th class="error-column">Errors</th>
            </tr>
            <xsl:for-each select="file[@name and count(error) > 0 and generate-id(.) = generate-id(key('files', @name))]">
                <xsl:sort data-type="number" order="descending" select="count(error)"/>
                <xsl:variable name="currentName" select="@name" />
                <xsl:variable name="errorCount" select="count(error)"/>
                <tr>
                    <xsl:call-template name="alternated-row"/>
                    <td class="file-column"><a href="#f-{@name}"><xsl:value-of select="@name"/></a></td>
                    <td class="error-column"><xsl:value-of select="$errorCount"/></td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>

    <xsl:template match="file">
        <xsl:if test="count(error) > 0">
            <a name="f-{@name}"></a>
            <h3>Name <xsl:value-of select="@name"/></h3>

            <table class="log" border="0" cellpadding="5" cellspacing="2" width="100%">
                <tr>
                    <th class="error-column">Error Description</th>
                    <th class="line-column">Line</th>
                </tr>
                <xsl:for-each select="error">
                    <xsl:sort data-type="number" order="ascending" select="@line"/>
                    <tr>
                        <xsl:call-template name="alternated-row"/>
                        <td class="error-column"><xsl:value-of select="@message"/></td>
                        <td class="line-column"><xsl:value-of select="@line"/></td>
                    </tr>
                </xsl:for-each>
            </table>
            <a href="#top">Back to top</a>
        </xsl:if>
    </xsl:template>

    <xsl:template match="checkstyle" mode="summary">
        <h3>Summary</h3>
        <xsl:variable name="totalFileCount" select="count(file[@name and generate-id(.) = generate-id(key('files', @name))])"/>
        <xsl:variable name="errorCount" select="count(file/error)"/>
        <table class="log" border="0" cellpadding="5" cellspacing="2" width="100%">
            <tr>
                <th>Total Files</th>
                <th class="summary-errors">Errors</th>
            </tr>
            <tr>
                <xsl:call-template name="alternated-row"/>
                <td><xsl:value-of select="$totalFileCount"/></td>
                <td><xsl:value-of select="$errorCount"/></td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template name="alternated-row">
        <xsl:attribute name="class">
            <xsl:if test="position() mod 2 = 1">a</xsl:if>
            <xsl:if test="position() mod 2 = 0">b</xsl:if>
        </xsl:attribute>
    </xsl:template>

</xsl:stylesheet>
