package com.coroutinesflow.marvel_home_feature_test

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    TestHomeViewModel::class,
    TestMarvelHomeLocalDataStore::class,
    TestMarvelHomeRemoteDataStore::class,
    TestMarvelHomeRepository::class,
    TestNetworkHandler::class
)
class TestMarvelHomeFeatureSuiteRunner