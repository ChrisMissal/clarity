package skadistats.clarity.two.framework;

import java.lang.annotation.Annotation;

public class EventProvider {

    private final Class<? extends Annotation> eventClass;
    private final Class<?> providerClass;

    public EventProvider(Class<? extends Annotation> eventClass, Class<?> providerClass) {
        this.eventClass = eventClass;
        this.providerClass = providerClass;
    }

    public Class<? extends Annotation> getEventClass() {
        return eventClass;
    }

    public Class<?> getProviderClass() {
        return providerClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventProvider that = (EventProvider) o;

        if (!eventClass.equals(that.eventClass)) return false;
        if (!providerClass.equals(that.providerClass)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventClass.hashCode();
        result = 31 * result + providerClass.hashCode();
        return result;
    }
}
