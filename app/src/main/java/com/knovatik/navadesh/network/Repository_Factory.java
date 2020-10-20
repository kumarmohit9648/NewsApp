package com.knovatik.navadesh.network;

import com.knovatik.navadesh.network.interfaces.Api;
import dagger.internal.Factory;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class Repository_Factory implements Factory<Repository> {
  private final Provider<Api> _apiProvider;

  public Repository_Factory(Provider<Api> _apiProvider) {
    this._apiProvider = _apiProvider;
  }

  @Override
  public Repository get() {
    return newInstance(_apiProvider.get());
  }

  public static Repository_Factory create(Provider<Api> _apiProvider) {
    return new Repository_Factory(_apiProvider);
  }

  public static Repository newInstance(Api _api) {
    return new Repository(_api);
  }
}
