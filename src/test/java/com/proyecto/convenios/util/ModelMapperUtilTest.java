package com.proyecto.convenios.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

class ModelMapperUtilTest {

    @Mock
    private ModelMapper modelMapper;

    private ModelMapperUtil modelMapperUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelMapperUtil = new ModelMapperUtil(modelMapper);
    }

    @Test
    void testMap() {
        // Arrange
        SourceClass source = new SourceClass("test");
        DestinationClass expectedDestination = new DestinationClass("test");
        when(modelMapper.map(source, DestinationClass.class)).thenReturn(expectedDestination);

        // Act
        DestinationClass result = modelMapperUtil.map(source, DestinationClass.class);

        // Assert
        assertEquals(expectedDestination, result);
        verify(modelMapper).map(source, DestinationClass.class);
    }

    @Test
    void testMapAll() {
        // Arrange
        List<SourceClass> sourceList = Arrays.asList(new SourceClass("test1"), new SourceClass("test2"));
        List<DestinationClass> expectedList = Arrays.asList(new DestinationClass("test1"),
                new DestinationClass("test2"));
        when(modelMapper.map(any(SourceClass.class), eq(DestinationClass.class))).thenReturn(expectedList.get(0),
                expectedList.get(1));

        // Act
        List<DestinationClass> result = modelMapperUtil.mapAll(sourceList, DestinationClass.class);

        // Assert
        assertEquals(expectedList, result);
        verify(modelMapper, times(2)).map(any(SourceClass.class), eq(DestinationClass.class));
    }

    private static class SourceClass {
        @SuppressWarnings("unused")
        private String value;

        public SourceClass(String value) {
            this.value = value;
        }
    }

    private static class DestinationClass {
        private String value;

        public DestinationClass(String value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            DestinationClass that = (DestinationClass) o;
            return Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }
}
