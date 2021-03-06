package com.dci.intellij.dbn.language.common.element.lookup;

import com.dci.intellij.dbn.language.common.TokenType;
import com.dci.intellij.dbn.language.common.element.impl.LeafElementType;
import com.dci.intellij.dbn.language.common.element.impl.TokenElementType;
import com.dci.intellij.dbn.language.common.element.impl.WrapperElementType;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class WrapperElementTypeLookupCache extends ElementTypeLookupCache<WrapperElementType> {

    public WrapperElementTypeLookupCache(WrapperElementType elementType) {
        super(elementType);
    }

/*
    @Override
    boolean initAsFirstPossibleLeaf(LeafElementType leaf, ElementType source) {
        ElementTypeLookupCache startTokenLC = getElementType().getBeginTokenElement().getLookupCache();
        ElementTypeLookupCache wrappedTokenLC = getElementType().getWrappedElement().getLookupCache();
        return startTokenLC.couldStartWithLeaf(leaf) ||
               (*/
/*getElementType().isWrappingOptional() && *//*
wrappedTokenLC.couldStartWithLeaf(leaf));
    }

    @Override
    boolean initAsFirstRequiredLeaf(LeafElementType leaf, ElementType source) {
        ElementTypeLookupCache startTokenLC = getElementType().getBeginTokenElement().getLookupCache();
        return startTokenLC.shouldStartWithLeaf(leaf);
    }
*/

    @Override
    public Set<LeafElementType> collectFirstPossibleLeafs(ElementLookupContext context, @Nullable Set<LeafElementType> bucket) {
        bucket = super.collectFirstPossibleLeafs(context, bucket);
        bucket = initBucket(bucket);
        bucket.add(elementType.getBeginTokenElement());
        return bucket;
    }

    @Override
    public Set<TokenType> collectFirstPossibleTokens(ElementLookupContext context, @Nullable Set<TokenType> bucket) {
        bucket = super.collectFirstPossibleTokens(context, bucket);
        bucket = initBucket(bucket);
        bucket.add(elementType.getBeginTokenElement().tokenType);
        return bucket;
    }

    @Override
    public boolean containsToken(TokenType tokenType) {
        return getBeginTokenElement().tokenType == tokenType ||
                getEndTokenElement().tokenType == tokenType ||
                elementType.wrappedElement.lookupCache.containsToken(tokenType);
    }

    @Override
    public boolean containsLeaf(LeafElementType leafElementType) {
        return getBeginTokenElement() == leafElementType ||
                getEndTokenElement() == leafElementType ||
                elementType.wrappedElement.lookupCache.containsLeaf(leafElementType);
    }

    @Override
    public Set<TokenType> getFirstPossibleTokens() {
        Set<TokenType> tokenTypes = initBucket(null);
        tokenTypes.add(getBeginTokenElement().tokenType);
        elementType.wrappedElement.lookupCache.collectFirstPossibleTokens(tokenTypes);
        return tokenTypes;
    }

    @Override
    public Set<TokenType> getFirstRequiredTokens() {
        Set<TokenType> tokenTypes = initBucket(null);
        tokenTypes.add(getBeginTokenElement().tokenType);
        return tokenTypes;
    }

    @Override
    public boolean couldStartWithLeaf(LeafElementType leafElementType) {
        return getBeginTokenElement() == leafElementType || elementType.wrappedElement.lookupCache.couldStartWithLeaf(leafElementType);
    }

    @Override
    public boolean shouldStartWithLeaf(LeafElementType leafElementType) {
        return getBeginTokenElement() == leafElementType;
    }

    @Override
    public boolean couldStartWithToken(TokenType tokenType) {
        return getBeginTokenElement().lookupCache.couldStartWithToken(tokenType) || elementType.wrappedElement.lookupCache.couldStartWithToken(tokenType);
    }

    @Override
    public Set<LeafElementType> getFirstPossibleLeafs() {
        Set<LeafElementType> firstPossibleLeafs = initBucket(null);
        firstPossibleLeafs.add(getBeginTokenElement());
        return firstPossibleLeafs;
    }

    @Override
    public Set<LeafElementType> getFirstRequiredLeafs() {
        Set<LeafElementType> firstRequiredLeafs = initBucket(null);
        firstRequiredLeafs.add(getBeginTokenElement());
        return firstRequiredLeafs;
    }

    @Override
    public boolean startsWithIdentifier() {
        return false;
    }

    @Override
    public boolean isFirstPossibleToken(TokenType tokenType) {
        return couldStartWithToken(tokenType);
    }

    @Override
    public boolean isFirstRequiredToken(TokenType tokenType) {
        return getBeginTokenElement().tokenType == tokenType;
    }

    private TokenElementType getBeginTokenElement() {
        return elementType.getBeginTokenElement();
    }

    private TokenElementType getEndTokenElement() {
        return this.elementType.getEndTokenElement();
    }
}