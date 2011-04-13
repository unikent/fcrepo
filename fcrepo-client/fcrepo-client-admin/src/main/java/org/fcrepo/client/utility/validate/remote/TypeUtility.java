/* The contents of this file are subject to the license and copyright terms
 * detailed in the license directory at the root of the source tree (also
 * available online at http://fedora-commons.org/license/).
 */

package org.fcrepo.client.utility.validate.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.fcrepo.client.utility.validate.types.DatastreamInfo;
import org.fcrepo.client.utility.validate.types.RelationshipInfo;
import org.fcrepo.server.search.Condition;
import org.fcrepo.server.search.FieldSearchQuery;
import org.fcrepo.server.search.FieldSearchResult;
import org.fcrepo.server.search.ObjectFields;
import org.fcrepo.server.types.gen.ComparisonOperator;
import org.fcrepo.server.types.gen.Datastream;
import org.fcrepo.server.types.gen.ListSession;
import org.fcrepo.server.types.gen.RelationshipTuple;
import org.fcrepo.server.utilities.DCField;
import org.fcrepo.utilities.DateUtility;



/**
 * A collection of utility methods for converting between local objects and
 * those that are generated by the WSDL-based API-M and API-A clients.
 *
 * @author Jim Blake
 */
public class TypeUtility {

    /**
     * Convert a local {@link FieldSearchQuery} into a WSDL-style
     * {@link org.fcrepo.server.types.gen.FieldSearchQuery FieldSearchQuery}.
     */
    public static org.fcrepo.server.types.gen.FieldSearchQuery convertFieldSearchQueryToGenFieldSearchQuery(FieldSearchQuery fsq) {
        org.fcrepo.server.types.gen.Condition[] genConditions =
                fsq.getConditions() == null ? null
                        : convertConditionsListToGenConditionsArray(fsq
                                .getConditions());
        return new org.fcrepo.server.types.gen.FieldSearchQuery(genConditions, fsq
                .getTerms());
    }

    /**
     * Convert a {@link List} of local {@link Condition}s into an array of
     * WSDL-style {@link org.fcrepo.server.types.gen.Condition Condition}s.
     */
    public static org.fcrepo.server.types.gen.Condition[] convertConditionsListToGenConditionsArray(List<Condition> conditions) {
        List<org.fcrepo.server.types.gen.Condition> genConditions =
                new ArrayList<org.fcrepo.server.types.gen.Condition>();

        for (Condition condition : conditions) {
            genConditions.add(convertConditionToGenCondition(condition));
        }

        return genConditions.toArray(new org.fcrepo.server.types.gen.Condition[0]);
    }

    /**
     * Convert a local {@link Condition} into a WSDL-style
     * {@link org.fcrepo.server.types.gen.Condition Condition}.
     */
    public static org.fcrepo.server.types.gen.Condition convertConditionToGenCondition(Condition condition) {
        String opAbbr = condition.getOperator().getAbbreviation();

        ComparisonOperator compOperator;
        try {
            compOperator = ComparisonOperator.fromString(opAbbr);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("unrecognized comparison operator string: '"
                    + opAbbr + "'");
        }

        return new org.fcrepo.server.types.gen.Condition(condition.getProperty(),
                                                     compOperator,
                                                     condition.getValue());
    }

    /**
     * Convert a WSDL-style
     * {@link org.fcrepo.server.types.gen.FieldSearchResult FieldSearchResult} to a
     * local {@link FieldSearchResult}.
     */
    public static FieldSearchResult convertGenFieldSearchResultToFieldSearchResult(org.fcrepo.server.types.gen.FieldSearchResult fsr) {
        long completeListSize = 0;
        long cursor = 0;
        Date expirationDate = new Date(0);
        String token = null;

        ListSession listSession = fsr.getListSession();
        if (listSession != null) {
            completeListSize = listSession.getCompleteListSize().longValue();
            cursor = listSession.getCursor().longValue();
            expirationDate =
                    DateUtility.convertStringToDate(listSession
                            .getExpirationDate());
            token = listSession.getToken();
        }

        List<org.fcrepo.server.search.ObjectFields> objectFields =
                convertGenObjectFieldsArrayToObjectFieldsList(fsr
                        .getResultList());
        return new BasicFieldSearchResult(completeListSize,
                                          cursor,
                                          expirationDate,
                                          token,
                                          objectFields);
    }

    /**
     * Convert an array of WSDL-style
     * {@link org.fcrepo.server.types.gen.ObjectFields ObjectFields} objects to a
     * {@link List} of local {@link ObjectFields} objects.
     */
    public static List<ObjectFields> convertGenObjectFieldsArrayToObjectFieldsList(org.fcrepo.server.types.gen.ObjectFields[] objectFields) {
        List<ObjectFields> result = new ArrayList<ObjectFields>();
        for (org.fcrepo.server.types.gen.ObjectFields objectField : objectFields) {
            result.add(convertGenObjectFieldsToObjectFields(objectField));
        }
        return result;
    }

    /**
     * Convert a WSDL-style
     * {@link org.fcrepo.server.types.gen.ObjectFields ObjectFields} object to a
     * local {@link ObjectFields} object.
     */
    public static ObjectFields convertGenObjectFieldsToObjectFields(org.fcrepo.server.types.gen.ObjectFields source) {
        ObjectFields result = new ObjectFields();
        result.setPid(source.getPid());
        result.setLabel(source.getLabel());
        result.setState(source.getState());
        result.setOwnerId(source.getOwnerId());
        result.setCDate(DateUtility.convertStringToDate(source.getCDate()));
        result.setMDate(DateUtility.convertStringToDate(source.getMDate()));
        result.setDCMDate(DateUtility.convertStringToDate(source.getDcmDate()));
        result.titles().addAll(convertStringArray(source.getTitle()));
        result.subjects().addAll(convertStringArray(source.getSubject()));
        result.descriptions()
                .addAll(convertStringArray(source.getDescription()));
        result.publishers().addAll(convertStringArray(source.getPublisher()));
        result.contributors()
                .addAll(convertStringArray(source.getContributor()));
        result.dates().addAll(convertStringArray(source.getDate()));
        result.types().addAll(convertStringArray(source.getType()));
        result.formats().addAll(convertStringArray(source.getFormat()));
        result.identifiers().addAll(convertStringArray(source.getIdentifier()));
        result.sources().addAll(convertStringArray(source.getSource()));
        result.languages().addAll(convertStringArray(source.getLanguage()));
        result.relations().addAll(convertStringArray(source.getRelation()));
        result.coverages().addAll(convertStringArray(source.getCoverage()));
        result.rights().addAll(convertStringArray(source.getRights()));
        return result;
    }

    private static List<DCField> convertStringArray(String[] strings) {
        if (strings == null) {
            return Collections.emptyList();
        } else {
            ArrayList<DCField> dcFields = new ArrayList<DCField>();
            for (String field : strings) {
                dcFields.add(new DCField(field));
            }
            return dcFields;
        }
    }

    /**
     * Convert an array of WSDL-style {@link RelationshipTuple}s into a list of
     * local {@link RelationshipInfo} objects.
     */
    public static List<RelationshipInfo> convertGenRelsTupleArrayToRelationshipInfoList(RelationshipTuple[] array) {
        if (array == null) {
            return Collections.emptyList();
        }

        List<RelationshipInfo> list =
                new ArrayList<RelationshipInfo>(array.length);
        for (RelationshipTuple genTuple : array) {
            list.add(convertGenRelsTupleToRelationshipInfo(genTuple));
        }
        return list;
    }

    /**
     * Convert a WSDL-style {@link RelationshipTuple RelationshipTuple} into a
     * local {@link RelationshipInfo}.
     */
    public static RelationshipInfo convertGenRelsTupleToRelationshipInfo(RelationshipTuple genTuple) {
        return new RelationshipInfo(genTuple.getPredicate(), genTuple
                .getObject());
    }

    /**
     * Convert an array of WSDL-style {@link Datastream}s into a {@link Set} of
     * local {@link DatastreamInfo} objects.
     */
    public static Set<DatastreamInfo> convertGenDatastreamArrayToDatastreamInfoSet(Datastream[] genDss) {
        Set<DatastreamInfo> set = new HashSet<DatastreamInfo>(genDss.length);
        for (Datastream ds : genDss) {
            set.add(convertGenDatastreamDefToDatastreamInfo(ds));
        }
        return set;
    }

    /**
     * Convert a WSDL-style {@link Datastream} into a local
     * {@link DatastreamInfo}.
     */
    private static DatastreamInfo convertGenDatastreamDefToDatastreamInfo(Datastream ds) {
        return new DatastreamInfo(ds.getID(), ds.getMIMEType(), ds
                .getFormatURI());
    }

    /**
     * No need to instantiate since all methods are static.
     */
    private TypeUtility() {
        // Nothing to instantiate.
    }

}
