package com.coroutinesflow.hero_details_feature_test

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    TestHeroDetailsLocalDataStore::class,
    TestHeroDetailsRemoteDataStore::class,
    TestHeroDetailsRepository::class,
    TestHeroDetailsViewModel::class
)
class TestMarvelHeroDetailsFeatureSuiteRunner