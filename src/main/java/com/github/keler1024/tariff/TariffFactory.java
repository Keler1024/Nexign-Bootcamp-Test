package com.github.keler1024.tariff;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

public class TariffFactory {
    private static final Map<String, Supplier<ITariff>> tariffSupplierMap = Map.of(
            PerMinuteTariff.index, PerMinuteTariff::new,
            UnlimitedTariff.index, UnlimitedTariff::new,
            CommonTariff.index, CommonTariff::new
            );

    public static ITariff build(String index) {
        if (index == null || index.isBlank()) {
            throw new IllegalArgumentException("Provided index is null or doesn't have a value");
        }
        Supplier<ITariff> tariffSupplier = tariffSupplierMap.get(index);
        if (tariffSupplier == null) {
            throw new IllegalArgumentException("Tariff with index " + index + " doesn't exist");
        }
        return tariffSupplier.get();
    }
}
