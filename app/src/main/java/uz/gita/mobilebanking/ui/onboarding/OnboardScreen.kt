package uz.gita.mobilebanking.ui.onboarding

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.terrakok.modo.android.compose.ComposeScreen
import com.github.terrakok.modo.android.compose.uniqueScreenKey
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import uz.gita.mobilebanking.data.local.model.app.OnboardData
import uz.gita.mobilebanking.ui.onboarding.components.DotsIndicator
import uz.gita.mobilebanking.ui.theme.MobileBankingTheme
import uz.gita.mobilebanking.ui.theme.spacing
import uz.gita.mobilebanking.utils.customui.TextHint
import uz.gita.mobilebankingMBF.R

@Parcelize
class OnboardScreen(override val screenKey: String = uniqueScreenKey) :
    ComposeScreen(id = "OnboardScreen") {

    @Composable
    override fun Content() {
        val context = LocalContext.current
        Timber.d("Content context: $context")
        val viewModel: OnboardVM = viewModel<OnboardVMImpl>()
        OnboardScreenContent(viewModel::onEvent)
        viewModel.sideEffect {
            if (it == OnboardContract.SideEffect.FINISH) {
                (context as Activity).finish()
            }
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnboardScreenContent(
    event: (OnboardContract.Intent) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        ConstraintLayout {
            val (skipButton, pager, dotsIndicator, nextButton, startButton) = createRefs()
            val startGuideLine = createGuidelineFromStart(0.5f)
            val onboardData by remember { mutableStateOf(onboardList) }
            val pagerState = rememberPagerState()
            val coroutineScope = rememberCoroutineScope()

            BackHandler {
                if (pagerState.currentPage != 0) coroutineScope.launch {
                    pagerState.animateScrollToPage(
                        pagerState.currentPage - 1
                    )
                } else event(OnboardContract.Intent.BACK)
            }

            HorizontalPager(
                modifier = Modifier
                    .constrainAs(pager) {
                        linkTo(start = parent.start, end = parent.end)
                        linkTo(top = parent.top, bottom = parent.bottom)
                    }
                    .fillMaxSize(),
                count = onboardData.size,
                state = pagerState
            ) { page ->

                Column(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.spacing.spacing_64dp)
                ) {
                    Image(
                        modifier = Modifier.padding(MaterialTheme.spacing.spacing_64dp),
                        painter = painterResource(id = onboardData[page].image),
                        contentDescription = ""
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = onboardData[page].title),
                        style = MaterialTheme.typography.h4.copy(textAlign = TextAlign.Center)
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = MaterialTheme.spacing.spacing_16dp,
                                end = MaterialTheme.spacing.spacing_16dp,
                                top = MaterialTheme.spacing.spacing_8dp,
                                bottom = MaterialTheme.spacing.spacing_32dp
                            ),
                        text = stringResource(id = onboardData[page].description),
                        style = MaterialTheme.typography.body1.copy(textAlign = TextAlign.Center)
                    )
                }

            }

            TextHint(
                modifier = Modifier
                    .padding(
                        top = MaterialTheme.spacing.spacing_24dp,
                        end = MaterialTheme.spacing.spacing_24dp
                    )
                    .constrainAs(skipButton) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    },
                onClick = {
                    Timber.d("skip")
                    event(OnboardContract.Intent.SKIP)
                },
                text = R.string.skip,
            )

            DotsIndicator(
                modifier = Modifier
                    .padding(
                        start = MaterialTheme.spacing.spacing_24dp,
                        bottom = MaterialTheme.spacing.spacing_24dp
                    )
                    .constrainAs(dotsIndicator) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                totalDots = onboardData.size,
                selectedIndex = pagerState.currentPage
            )

            if (pagerState.currentPage != onboardData.size - 1) {
                Text(
                    modifier = Modifier
                        .padding(
                            end = MaterialTheme.spacing.spacing_24dp,
                            bottom = MaterialTheme.spacing.spacing_16dp
                        )
                        .constrainAs(nextButton) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            if (pagerState.currentPage != pagerState.pageCount - 1) {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                    text = stringResource(id = R.string.next)
                )
            } else {
                Button(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.spacing.spacing_24dp,
                            end = MaterialTheme.spacing.spacing_24dp,
                            bottom = MaterialTheme.spacing.spacing_8dp
                        )
                        .constrainAs(startButton) {
                            start.linkTo(startGuideLine)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .fillMaxWidth(0.5f)
                        .height(48.dp),
                    onClick = {
                        event(OnboardContract.Intent.START)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.secondary
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = stringResource(id = R.string.start),
                        style = MaterialTheme.typography.button
                    )
                }

            }
        }
    }
}

private val onboardList: List<OnboardData> = listOf(
    OnboardData(
        id = 1,
        image = R.drawable.onboarding1,
        title = R.string.onboard_title1,
        description = R.string.onboard_description1
    ),
    OnboardData(
        id = 2,
        image = R.drawable.onboarding2,
        title = R.string.onboard_title2,
        description = R.string.onboard_description2
    ),
    OnboardData(
        id = 3,
        image = R.drawable.onboarding3,
        title = R.string.onboard_title3,
        description = R.string.onboard_description3
    ),
    OnboardData(
        id = 4,
        image = R.drawable.onboarding4,
        title = R.string.onboard_title4,
        description = R.string.onboard_description4
    )
)


@[Preview Composable]
private fun OnboardScreenPreview() {
    MobileBankingTheme {
        OnboardScreenContent(event = {})
    }
}