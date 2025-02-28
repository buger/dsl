package com.structurizr.dsl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class ImpliedRelationshipsParserTests extends AbstractTests {

    private ImpliedRelationshipsParser parser = new ImpliedRelationshipsParser(false);

    @Test
    void test_parse_ThrowsAnException_WhenThereAreTooManyTokensAndNotRestricted() {
        try {
            parser.parse(context(), tokens("impliedRelationships", "boolean", "extra"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many tokens, expected: !impliedRelationships <true|false|fqn>", e.getMessage());
        }
    }

    @Test
    void test_parse_ThrowsAnException_WhenThereAreTooManyTokensAndRestricted() {
        try {
            parser = new ImpliedRelationshipsParser(true);
            parser.parse(context(), tokens("impliedRelationships", "boolean", "extra"));
            fail();
        } catch (Exception e) {
            assertEquals("Too many tokens, expected: !impliedRelationships <true|false>", e.getMessage());
        }
    }

    @Test
    void test_parse_ThrowsAnException_WhenNoFlagIsSpecifiedAndNotRestricted() {
        try {
            parser.parse(context(), tokens("impliedRelationships"));
            fail();
        } catch (Exception e) {
            assertEquals("Expected: !impliedRelationships <true|false|fqn>", e.getMessage());
        }
    }

    @Test
    void test_parse_ThrowsAnException_WhenNoFlagIsSpecifiedAndRestricted() {
        try {
            parser = new ImpliedRelationshipsParser(true);
            parser.parse(context(), tokens("impliedRelationships"));
            fail();
        } catch (Exception e) {
            assertEquals("Expected: !impliedRelationships <true|false>", e.getMessage());
        }
    }

    @Test
    void test_parse_SetsTheStrategy_WhenFalseIsSpecified() {
        parser.parse(context(), tokens("impliedRelationships", "false"));

        assertEquals("com.structurizr.model.DefaultImpliedRelationshipsStrategy", workspace.getModel().getImpliedRelationshipsStrategy().getClass().getCanonicalName());
    }

    @Test
    void test_parse_SetsTheStrategy_WhenTrueIsSpecified() {
        parser.parse(context(), tokens("impliedRelationships", "true"));

        assertEquals("com.structurizr.model.CreateImpliedRelationshipsUnlessAnyRelationshipExistsStrategy", workspace.getModel().getImpliedRelationshipsStrategy().getClass().getCanonicalName());
    }

    @Test
    void test_parse_SetsTheStrategy_WhenAFullyQualifiedClassNameIsSpecifiedAndNotRestricted() {
        parser.parse(context(), tokens("impliedRelationships", "com.structurizr.model.CreateImpliedRelationshipsUnlessSameRelationshipExistsStrategy"));

        assertEquals("com.structurizr.model.CreateImpliedRelationshipsUnlessSameRelationshipExistsStrategy", workspace.getModel().getImpliedRelationshipsStrategy().getClass().getCanonicalName());
    }

    @Test
    void test_parse_SetsTheStrategy_WhenAFullyQualifiedClassNameIsSpecifiedAndRestricted() {
        try {
            parser = new ImpliedRelationshipsParser(true);
            parser.parse(context(), tokens("impliedRelationships", "com.structurizr.model.CreateImpliedRelationshipsUnlessSameRelationshipExistsStrategy"));
            fail();
        } catch (Exception e) {
            assertEquals("Custom implied relationship strategies are not available when running in restricted mode, expected: !impliedRelationships <true|false>", e.getMessage());
        }
    }

}