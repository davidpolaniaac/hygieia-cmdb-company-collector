package com.capitalone.dashboard.collector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Random;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.capitalone.dashboard.misc.HygieiaException;
import com.capitalone.dashboard.model.ChangeOrder;
import com.capitalone.dashboard.model.Cmdb;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Incident;
import com.capitalone.dashboard.repository.ChangeOrderRepository;
import com.capitalone.dashboard.repository.CmdbItemRepository;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.ComponentRepository;
import com.capitalone.dashboard.repository.IncidentRepository;

@RunWith(MockitoJUnitRunner.class)
public class CmdbCollectorTaskTest {
    @Mock private CmdbItemRepository cmdbRepository;
    @Mock private CmdbClient cmdbClient;
    @Mock private IncidentRepository incidentRepository;
    @Mock private ComponentRepository componentRepository;
    @Mock private ChangeOrderRepository changeOrderRepository;
    @Mock private CollectorItemRepository collectorItemRepository;

    @InjectMocks private CmdbCollectorTask task;

    @Test
    public void shouldGetCollector() {
        Collector collector = collector();
        assertEquals(CollectorType.CMDB, collector.getCollectorType());
        assertTrue(collector.isOnline());
        assertTrue(collector.isEnabled());
    }

    private void testCollectorAction(String collectorAction) {
        CollectorType collectorType = null;
        if ("CmdbIncident".equalsIgnoreCase(collectorAction)) {
            collectorType = CollectorType.Incident;
        } else {
            collectorType = CollectorType.CMDB;
        }
        System.setProperty("collector.action", collectorAction);
        Collector collector = collector(collectorAction);

        assertEquals(collectorAction, collector.getName());
        assertEquals(collectorType, collector.getCollectorType());
        assertTrue(collector.isOnline());
        assertTrue(collector.isEnabled());
    }

    @Test
    public void shouldGetIncidentCollector() {
        testCollectorAction("cmdb");
    }

    @Test
    public void shouldGetChangeCollector() {
        testCollectorAction("CmdbChange");
    }

    @Test
    public void shouldGetCmdbCollector() {
        testCollectorAction("Cmdb");
    }

    @Test
    public void collect_testCollect() throws HygieiaException {
        when(cmdbClient.getApps()).thenReturn(getMockList());
        when(cmdbRepository.findAll()).thenReturn(getMockList());
        when(cmdbRepository.findAllByValidConfigItem(true)).thenReturn(getMockList());

        CmdbCollector collector =collector();
        collector.setId(new ObjectId("111ca42a258ad365fbb64ecc"));

  
        task.collect(collector);

        verify(cmdbRepository).findByConfigurationItemAndConfigurationItemSubTypeAndConfigurationItemType("ASVTEST", "ASVTEST", "ASVTEST");
        assertNull(cmdbRepository.findByConfigurationItemAndConfigurationItemSubType("123", "test"));
    }

    public ArrayList<Cmdb> getMockList() {
        ArrayList<Cmdb> mockList = new ArrayList<>();

        Cmdb cmdb = new Cmdb();

        cmdb.setId(new ObjectId("1c1ca42a258ad365fbb64ecc"));
        cmdb.setConfigurationItem("ASVTEST");
        cmdb.setConfigurationItemSubType("ASVTEST");
        cmdb.setConfigurationItemType("ASVTEST");
        cmdb.setOwnerDept("TESTLOB0");
        cmdb.setAssignmentGroup("PROCESS_TEST_AG0");
        cmdb.setAppServiceOwner("John Handcock");
        cmdb.setBusinessOwner("John Doe");
        cmdb.setSupportOwner("John Handcock");
        cmdb.setDevelopmentOwner("Jain Doe");
        cmdb.setCollectorItemId(new ObjectId("111ca42a258ad365fbb64ecc"));
        mockList.add(cmdb);

        cmdb = new Cmdb();
        cmdb.setId(new ObjectId("1c1ca42a258ad365fbb64ecd"));
        cmdb.setConfigurationItem("ASVTEST123");
        cmdb.setOwnerDept("TESTLOB1");
        cmdb.setAssignmentGroup("PROCESS_TEST_AG1");
        cmdb.setAppServiceOwner("Jain Doe");
        cmdb.setBusinessOwner("John Handcock");
        cmdb.setSupportOwner("John Handcock");
        cmdb.setDevelopmentOwner("Jain Doe");
        cmdb.setCollectorItemId(new ObjectId("111ca42a258ad365fbb64ecc"));
        mockList.add(cmdb);

        cmdb = new Cmdb();
        cmdb.setId(new ObjectId("1c1ca42a258ad365fbb64ece"));
        cmdb.setConfigurationItem("ASVTEST1234");
        cmdb.setOwnerDept("TESTLOB2");
        cmdb.setAssignmentGroup("PROCESS_TEST_AG2");
        cmdb.setAppServiceOwner("John Handcock");
        cmdb.setBusinessOwner("John Doe");
        cmdb.setSupportOwner("John Doe");
        cmdb.setDevelopmentOwner("John Handcock");
        cmdb.setCollectorItemId(new ObjectId("111ca42a258ad365fbb64ecc"));
        mockList.add(cmdb);

        cmdb = new Cmdb();
        cmdb.setId(new ObjectId("1c1ca42a258ad365fbb64ecf"));
        cmdb.setConfigurationItem("ASVTEST12346");
        cmdb.setOwnerDept("TESTLOB3");
        cmdb.setAssignmentGroup("PROCESS_TEST_AG3");
        cmdb.setAppServiceOwner("John Doe");
        cmdb.setBusinessOwner("Jain Doe");
        cmdb.setSupportOwner("John Doe");
        cmdb.setDevelopmentOwner("John Handcock");
        cmdb.setCollectorItemId(new ObjectId("111ca42a258ad365fbb64ecc"));
        mockList.add(cmdb);
        return mockList;
    }


    public ArrayList<ChangeOrder> getMockChangeOrderList(){
        ObjectId collectorItemId = new ObjectId(createGuid());
        ArrayList<ChangeOrder> mockList = new ArrayList<>();
        ChangeOrder changeOrder = new ChangeOrder();

        changeOrder.setId(new ObjectId(createGuid()));
        changeOrder.setCollectorItemId(collectorItemId);
        changeOrder.setChangeID("C012345");
        changeOrder.setAssignmentGroup("HYGIEIA");
        mockList.add(changeOrder);

        changeOrder = new ChangeOrder();
        changeOrder.setId(new ObjectId(createGuid()));
        changeOrder.setCollectorItemId(collectorItemId);
        changeOrder.setChangeID("C012346");
        changeOrder.setAssignmentGroup("GITHUB");
        mockList.add(changeOrder);

        changeOrder = new ChangeOrder();
        changeOrder.setId(new ObjectId(createGuid()));
        changeOrder.setCollectorItemId(collectorItemId);
        changeOrder.setChangeID("C012347");
        changeOrder.setAssignmentGroup("JENKINS");
        mockList.add(changeOrder);

        changeOrder = new ChangeOrder();
        changeOrder.setId(new ObjectId(createGuid()));
        changeOrder.setCollectorItemId(collectorItemId);
        changeOrder.setChangeID("C012348");
        changeOrder.setAssignmentGroup("ARTIFACTORY");
        mockList.add(changeOrder);

        return mockList;
    }


//    @Test
//    public void getCollectorItemIdList_Test () {
//        List<AppComponent> componentList = getComponentList();
//        when(componentRepository.findByIncidentCollectorItems(true)).thenReturn(componentList);
//
//        List<ObjectId> expectedObjectIdList = new ArrayList<>();
//        expectedObjectIdList.add(new ObjectId("1c1ca42a258ad365fbb64ecf"));
//        expectedObjectIdList.add(new ObjectId("111ca42a258ad365fbb64ecc"));
//
//
//        List<ObjectId> objectIdList = task.getCollectorItemIdList();
//
//        Assert.assertArrayEquals(expectedObjectIdList.toArray(), objectIdList.toArray());
//    }

//    private List<AppComponent> getComponentList() {
//        AppComponent comp1 = new AppComponent();
//        CollectorItem collectorItem1 = new CollectorItem();
//        collectorItem1.setId(new ObjectId("1c1ca42a258ad365fbb64ecf"));
//        comp1.addCollectorItem(CollectorType.Incident, collectorItem1);
//
//        AppComponent comp2 = new AppComponent();
//        CollectorItem collectorItem2 = new CollectorItem();
//        collectorItem2.setId(new ObjectId("111ca42a258ad365fbb64ecc"));
//        comp2.addCollectorItem(CollectorType.Incident, collectorItem2);
//
//        List<AppComponent> componentList = new ArrayList<>();
//        componentList.add(comp1);
//        componentList.add(comp2);
//
//        return componentList;
//    }

    public ArrayList<Incident> getMockIncidentList() {
        ObjectId collectorItemId = new ObjectId(createGuid());
        ArrayList<Incident> mockList = new ArrayList<>();
        Incident incident = new Incident();

        incident.setId(new ObjectId(createGuid()));
        incident.setIncidentID("IR01234");
        incident.setAffectedItem("Component-IR01234");
        incident.setCollectorItemId(collectorItemId);
        incident.setPrimaryAssignmentGroup("HYGIEIA");
        mockList.add(incident);

        incident = new Incident();
        incident.setId(new ObjectId(createGuid()));
        incident.setIncidentID("IR01235");
        incident.setAffectedItem("Component-IR01235");
        incident.setCollectorItemId(collectorItemId);
        incident.setPrimaryAssignmentGroup("JENKINS");
        mockList.add(incident);

        incident = new Incident();
        incident.setId(new ObjectId(createGuid()));
        incident.setIncidentID("IR01236");
        incident.setAffectedItem("Component-IR01236");
        incident.setCollectorItemId(collectorItemId);
        incident.setPrimaryAssignmentGroup("ARTIFACTORY");
        mockList.add(incident);

        incident = new Incident();
        incident.setId(new ObjectId(createGuid()));
        incident.setIncidentID("IR01237");
        incident.setAffectedItem("Component-IR01237");
        incident.setCollectorItemId(collectorItemId);
        incident.setPrimaryAssignmentGroup("GITHUB");
        mockList.add(incident);

        return mockList;
    }


    private CmdbCollector collector() {
        return CmdbCollector.prototype();
    }

    private CmdbCollector collector(String collectorAction) {
        return CmdbCollector.prototype(collectorAction);
    }

    public static String createGuid() {
        byte[]  bytes = new byte[12];
        new Random().nextBytes(bytes);

        char[] hexArray = "0123456789abcdef".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}