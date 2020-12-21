package com.dci.intellij.dbn.data.export;

import com.dci.intellij.dbn.common.state.PersistentStateElement;
import com.dci.intellij.dbn.common.util.Cloneable;
import org.jdom.Element;

import java.io.File;
import java.nio.charset.Charset;

import static com.dci.intellij.dbn.common.options.setting.SettingsSupport.*;

public class DataExportInstructions implements PersistentStateElement, Cloneable {
    private boolean createHeader = true;
    private boolean quoteValuesContainingSeparator = true;
    private boolean quoteAllValues = false;
    private String valueSeparator;
    private String fileName;
    private String fileLocation;
    private Scope scope = Scope.GLOBAL;
    private Destination destination = Destination.FILE;
    private DataExportFormat format = DataExportFormat.EXCEL;
    private String baseName;
    private Charset charset = Charset.defaultCharset();

    public DataExportInstructions() {
    }

    public File getFile() {
        return new File(fileLocation, fileName);
    }

    public boolean isCreateHeader() {
        return this.createHeader;
    }

    public boolean isQuoteValuesContainingSeparator() {
        return this.quoteValuesContainingSeparator;
    }

    public boolean isQuoteAllValues() {
        return this.quoteAllValues;
    }

    public String getValueSeparator() {
        return this.valueSeparator;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getFileLocation() {
        return this.fileLocation;
    }

    public Scope getScope() {
        return this.scope;
    }

    public Destination getDestination() {
        return this.destination;
    }

    public DataExportFormat getFormat() {
        return this.format;
    }

    public String getBaseName() {
        return this.baseName;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCreateHeader(boolean createHeader) {
        this.createHeader = createHeader;
    }

    public void setQuoteValuesContainingSeparator(boolean quoteValuesContainingSeparator) {
        this.quoteValuesContainingSeparator = quoteValuesContainingSeparator;
    }

    public void setQuoteAllValues(boolean quoteAllValues) {
        this.quoteAllValues = quoteAllValues;
    }

    public void setValueSeparator(String valueSeparator) {
        this.valueSeparator = valueSeparator;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setFormat(DataExportFormat format) {
        this.format = format;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof DataExportInstructions)) return false;
        final DataExportInstructions other = (DataExportInstructions) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.isCreateHeader() != other.isCreateHeader()) return false;
        if (this.isQuoteValuesContainingSeparator() != other.isQuoteValuesContainingSeparator()) return false;
        if (this.isQuoteAllValues() != other.isQuoteAllValues()) return false;
        final Object this$valueSeparator = this.getValueSeparator();
        final Object other$valueSeparator = other.getValueSeparator();
        if (this$valueSeparator == null ? other$valueSeparator != null : !this$valueSeparator.equals(other$valueSeparator))
            return false;
        final Object this$fileName = this.getFileName();
        final Object other$fileName = other.getFileName();
        if (this$fileName == null ? other$fileName != null : !this$fileName.equals(other$fileName)) return false;
        final Object this$fileLocation = this.getFileLocation();
        final Object other$fileLocation = other.getFileLocation();
        if (this$fileLocation == null ? other$fileLocation != null : !this$fileLocation.equals(other$fileLocation))
            return false;
        final Object this$scope = this.getScope();
        final Object other$scope = other.getScope();
        if (this$scope == null ? other$scope != null : !this$scope.equals(other$scope)) return false;
        final Object this$destination = this.getDestination();
        final Object other$destination = other.getDestination();
        if (this$destination == null ? other$destination != null : !this$destination.equals(other$destination))
            return false;
        final Object this$format = this.getFormat();
        final Object other$format = other.getFormat();
        if (this$format == null ? other$format != null : !this$format.equals(other$format)) return false;
        final Object this$baseName = this.getBaseName();
        final Object other$baseName = other.getBaseName();
        if (this$baseName == null ? other$baseName != null : !this$baseName.equals(other$baseName)) return false;
        final Object this$charset = this.getCharset();
        final Object other$charset = other.getCharset();
        if (this$charset == null ? other$charset != null : !this$charset.equals(other$charset)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof DataExportInstructions;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + (this.isCreateHeader() ? 79 : 97);
        result = result * PRIME + (this.isQuoteValuesContainingSeparator() ? 79 : 97);
        result = result * PRIME + (this.isQuoteAllValues() ? 79 : 97);
        final Object $valueSeparator = this.getValueSeparator();
        result = result * PRIME + ($valueSeparator == null ? 43 : $valueSeparator.hashCode());
        final Object $fileName = this.getFileName();
        result = result * PRIME + ($fileName == null ? 43 : $fileName.hashCode());
        final Object $fileLocation = this.getFileLocation();
        result = result * PRIME + ($fileLocation == null ? 43 : $fileLocation.hashCode());
        final Object $scope = this.getScope();
        result = result * PRIME + ($scope == null ? 43 : $scope.hashCode());
        final Object $destination = this.getDestination();
        result = result * PRIME + ($destination == null ? 43 : $destination.hashCode());
        final Object $format = this.getFormat();
        result = result * PRIME + ($format == null ? 43 : $format.hashCode());
        final Object $baseName = this.getBaseName();
        result = result * PRIME + ($baseName == null ? 43 : $baseName.hashCode());
        final Object $charset = this.getCharset();
        result = result * PRIME + ($charset == null ? 43 : $charset.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "DataExportInstructions(createHeader=" + this.isCreateHeader() + ", quoteValuesContainingSeparator=" + this.isQuoteValuesContainingSeparator() + ", quoteAllValues=" + this.isQuoteAllValues() + ", valueSeparator=" + this.getValueSeparator() + ", fileName=" + this.getFileName() + ", fileLocation=" + this.getFileLocation() + ", scope=" + this.getScope() + ", destination=" + this.getDestination() + ", format=" + this.getFormat() + ", baseName=" + this.getBaseName() + ", charset=" + this.getCharset() + ")";
    }

    public enum Scope{
        GLOBAL,
        SELECTION
    }

    public enum Destination{
        FILE,
        CLIPBOARD
    }

    @Override
    public DataExportInstructions clone() {
        return PersistentStateElement.cloneElement(this, new DataExportInstructions());
    }

    /***********************************************
     *            PersistentStateElement           *
     ***********************************************/
    @Override
    public void writeState(Element element) {
        Element child = new Element("export-instructions");
        element.addContent(child);

        setBoolean(child, "create-header", createHeader);
        setBoolean(child, "quote-values-containing-separator", quoteValuesContainingSeparator);
        setBoolean(child, "quote-all-values", quoteAllValues);
        setString(child, "value-separator", valueSeparator);
        setString(child, "file-name", fileName);
        setString(child, "file-location", fileLocation);
        setString(child, "scope", scope.name());
        setString(child, "destination", destination.name());
        setString(child, "format", format.name());
        setString(child, "charset", charset.name());
    }

    @Override
    public void readState(Element element) {
        Element child = element.getChild("export-instructions");
        if (child != null) {
            createHeader = getBoolean(child, "create-header", createHeader);
            quoteValuesContainingSeparator = getBoolean(child, "quote-values-containing-separator", quoteValuesContainingSeparator);
            quoteAllValues = getBoolean(child, "quote-all-values", quoteAllValues);
            valueSeparator = getString(child, "value-separator", valueSeparator);
            fileName = getString(child, "file-name", fileName);
            fileLocation = getString(child, "file-location", fileLocation);
            scope = Scope.valueOf(getString(child, "scope", scope.name()));
            destination = Destination.valueOf(getString(child, "destination", destination.name()));
            format = DataExportFormat.valueOf(getString(child, "format", format.name()));
            charset = Charset.forName(getString(element, "charset", charset.name()));
        }
    }
}
