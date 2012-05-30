package org.apache.rave.portal.model.conversion;

import org.apache.rave.model.ModelConverter;
import org.apache.rave.portal.model.Person;
import org.apache.rave.portal.model.PersonImpl;
import org.junit.Test;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 */
public class ConvertingListProxyFactoryTest {

    @Test(expected = IllegalStateException.class)
    public void unsetInstance() {
        ConvertingListProxyFactory.getInstance();
    }

    @Test
    public void createProxy() {
        List<ModelConverter> converters = new ArrayList<ModelConverter>();
        ModelConverter converterMock = createMock(ModelConverter.class);
        expect(converterMock.getSourceType()).andReturn(Person.class).anyTimes();
        converters.add(converterMock);
        replay(converterMock);
        List<PersonImpl> underlying = new ArrayList<PersonImpl>();
        new ConvertingListProxyFactory(converters);

        List<Person> personProxy = ConvertingListProxyFactory.getInstance().createProxyList(Person.class, underlying);
        assertThat(Proxy.isProxyClass(personProxy.getClass()), is(true));
    }

    @Test
    public void proxyAdd() {
        List<ModelConverter> converters = new ArrayList<ModelConverter>();
        ModelConverter converterMock = createMock(ModelConverter.class);
        expect(converterMock.getSourceType()).andReturn(Person.class).anyTimes();
        converters.add(converterMock);
        Person personImpl1 = new PersonImpl();
        PersonImpl personImpl2 = new PersonImpl();
        expect(converterMock.convert(personImpl1)).andReturn(personImpl2);
        replay(converterMock);
        List<PersonImpl> underlying = createMock(List.class);
        expect(underlying.add(personImpl2)).andReturn(true);
        replay(underlying);
        new ConvertingListProxyFactory(converters);


        List<Person> personProxy = ConvertingListProxyFactory.getInstance().createProxyList(Person.class, underlying);
        Boolean good = personProxy.add(personImpl1);
        assertThat(good, is(true));
        verify(converterMock);
        verify(underlying);
    }

    @Test
    public void proxySet() {
        List<ModelConverter> converters = new ArrayList<ModelConverter>();
        ModelConverter converterMock = createMock(ModelConverter.class);
        expect(converterMock.getSourceType()).andReturn(Person.class).anyTimes();
        converters.add(converterMock);
        Person personImpl1 = new PersonImpl();
        Person personImpl2 = new PersonImpl();
        expect(converterMock.convert(personImpl1)).andReturn(personImpl2);
        replay(converterMock);
        List<PersonImpl> underlying = createMock(List.class);
        expect(underlying.set(0, (PersonImpl)personImpl2)).andReturn((PersonImpl) personImpl2);
        replay(underlying);
        new ConvertingListProxyFactory(converters);

        List<Person> personProxy = ConvertingListProxyFactory.getInstance().createProxyList(Person.class, underlying);
        Person good = personProxy.set(0, personImpl1);
        assertThat(good, is(sameInstance(personImpl2)));
        verify(converterMock);
        verify(underlying);
    }

    @Test
    public void proxyAddAll() {

        Person personImpl1 = new PersonImpl();
        Person personImpl2 = new PersonImpl();
        Person personImpl3 = new PersonImpl();
        Person personImpl4 = new PersonImpl();

        List<ModelConverter> converters = new ArrayList<ModelConverter>();
        ModelConverter converterMock = createMock(ModelConverter.class);
        expect(converterMock.getSourceType()).andReturn(Person.class).anyTimes();
        converters.add(converterMock);
        expect(converterMock.convert(personImpl1)).andReturn(personImpl2);
        expect(converterMock.convert(personImpl3)).andReturn(personImpl4);
        replay(converterMock);

        List<Person> toAdd = new ArrayList<Person>();
        toAdd.add(personImpl1);
        toAdd.add(personImpl3);

        List<PersonImpl> underlying = createMock(List.class);
        expect(underlying.addAll(isA(List.class))).andReturn(true);
        replay(underlying);
        new ConvertingListProxyFactory(converters);

        List<Person> personProxy = ConvertingListProxyFactory.getInstance().createProxyList(Person.class, underlying);
        Boolean good = personProxy.addAll(toAdd);
        assertThat(good, is(true));
        verify(converterMock);
        verify(underlying);
    }
}