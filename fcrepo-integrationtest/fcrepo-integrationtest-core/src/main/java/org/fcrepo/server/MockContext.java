/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */
package org.fcrepo.server;

import java.util.Date;
import java.util.Iterator;


/**
 * Context object for testing.
 * Currently, only getSubjectValue() and now() are implemented.
 *
 * @author Edwin Shin
 * @version $Id$
 */
public class MockContext
        implements Context {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Iterator actionAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Iterator environmentAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getActionValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getActionValues(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public MultiValueMap getEnvironmentAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getEnvironmentValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getEnvironmentValues(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean getNoOp() {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getResourceValue(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String[] getResourceValues(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public String getSubjectValue(String name) {
        return "fedoraAdmin";
    }

    /**
     * {@inheritDoc}
     */
    public String[] getSubjectValues(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public int nActionValues(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public int nEnvironmentValues(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public int nResourceValues(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public int nSubjectValues(String name) {
        // TODO Auto-generated method stub
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public Date now() {
        return new Date();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Iterator resourceAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void setActionAttributes(MultiValueMap actionAttributes) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    public void setResourceAttributes(MultiValueMap resourceAttributes) {
        // TODO Auto-generated method stub

    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Iterator subjectAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

}
