package com.system.watchCar.interfaces;

public interface IArtigo {

    IArtigo setIdArtigo(Long id);
    Long getIdArtigo();

    IArtigo setCodArtigo(String cod);
    String getCodArtigo();
    default String getRubrica() {
        if(getCodArtigo().contains(",")){
            String[] partes = getCodArtigo().split(",");
            return partes[1].trim();
        }
        return null;
    }

    IArtigo setDescricaoArtigo(String descricao);
    String getDescricaoArtigo();

    default <A extends IArtigo> A toArtigo(Class<A> clazz) {
        try {
            A instance = clazz.getDeclaredConstructor().newInstance();
            instance.setIdArtigo(getIdArtigo());
            instance.setCodArtigo(getCodArtigo());
            instance.setDescricaoArtigo(getDescricaoArtigo());
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao definir os valores do artigo: " + e.getMessage(), e);
        }
    }
}
