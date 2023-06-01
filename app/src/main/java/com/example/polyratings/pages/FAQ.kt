package com.example.polyratings.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polyratings.data.FaqItem
import com.example.polyratings.ui.theme.DarkGreen10
import com.example.polyratings.ui.theme.DarkGreen90
import com.example.polyratings.ui.theme.GreenGrey30

@Composable
fun FaqScreen(
    modifier: Modifier = Modifier
){
    val items = listOf(
        FaqItem("Q1. How do I know the ratings on a professor's page come from students?", "Short answer: you don't.\n" +
                "In fact, it's potentially worse than that... you don't even know that someone rating a professor, assuming they are a student, which is a huge assumption to make, ever took a professor's class.\n" +
                "Polyratings does everything in its power to review questionable postings brought to our attention, but a function of Polyratings' privacy guarantee is lack of authentication and login. This means that a professor could post positive ratings about themselves to their pages, or negative ratings about other professors (both of which have happened in the past).\n" +
                "We have been looking into ways of curbing this practice, but for now, Polyratings users will have to rely on their own judgement in determining which ratings to consider to be accurate; if you think about it, that requirement is no different than information you get from any other source.\n" +
                "If you believe a rating comes from a questionable source, please report it using the flag next to the rating. Leaving a detailed reason for the report as well as a contact email for a more complex case go a long way to resolve reports."),
        FaqItem("Q2. What are your guidelines regarding comments?", "The standard by which we judge all comments is a simple one: value.\n" +
                "We do not judge comments based upon the words they contain or the way they express their opinion, but if a comment is reported as inappropriate, we look to see what value it adds to both Polyratings and to Cal Poly students in general.\n" +
                "Calling a professor names is not only immature, but does not add value.\n" +
                "Posting anything but a comment (emails, test questions, etc.) about the professor does not add value.\n" +
                "Replying to other comments instead of giving your own opinion on the professor does not add value.\n" +
                "Value to the Cal Poly community is the gold standard by which we rate comments when problems are brought to our attention... if the comment lacks value, it will be deleted."),
        FaqItem("Q3. Why do you let inappropriate comments be posted in the first place?", "Polyratings' staff does not have time to read and approve every comment.\n" +
                "As such, we only hear about inappropriate comments after the fact; just because a comment appears does not mean that it's been reviewed and deemed acceptable.\n" +
                "As an aside, every rating is sent through a machine learning model that tries to check for toxicity and inappropriate language. This catches some of the worst offending ratings but can not catch all of them."),
        FaqItem("Q4. I made a comment about a professor, but I've thought about it, and I wish I hadn't posted my comment; will you remove/edit it for me?", "No.\n" +
                "If we made time to personally edit every student's comments, we'd never have time for our own school work. Think before you post.\n" +
                "Besides, there's the side issue of verifying that the person who's requesting we remove or edit the post is the one who really wrote it, which opens up a whole different can of worms.\n" +
                "As such, any requests to edit or delete comments will be ignored."),
        FaqItem("Q5. I have this really cool feature I'd like you to implement; will you write it for me and put it in Polyratings?", "Write us an issue on Github. We love to hear feedback and appreciate any help we can get. You can also join our Discord server. If you are CS minded you can even make your idea a reality.\n" +
                "Polyratings 4.0 is fully open source meaning that anyone can contribute. If you would like something changed or would like to implement a new feature, open a pull request!"),
        FaqItem("Q5. I'm a student/professor, and I've seen a comment you wrote on your website and I'm going to sue the crap out of you if you don't take it down!", "Despite the fact that this is not a question, we often get comments like this from professors and occasionally from students (if you can believe it) and we'd like to clarify our position on these types of emails.\n" +
                "In a nutshell, you can't sue Polyratings. You may think a comment about you is defamatory and libelous, and it may very well be.\n" +
                "But, we didn't write the comment. The comment is not ours; it's the property of the student who wrote it and while you're welcome to sue the author (assuming you can find out who they are), you really can't sue Polyratings, because we haven't broken any laws (and you wouldn't get any money out of us poor college students anyway).\n" +
                "The Communications Decency Act of 1996 protects Internet service providers (ISPs) and website operators from being sued for original comments made by visitors to the site. And while the CDA itself has been struck down by the Supreme Court for other reasons, courts, in cases involving Yahoo! and AOL, have generally followed the precedent set by the CDA that ISPs and website operators carry immunity from being sued for content posted by others.\n" +
                "So please... if you find inappropriate content in reference to you on Polyratings, please notify us. But don't write a scathing email threatening to sue us. For one, it makes your credibility go way down because you're threatening something you can't deliver on and secondly, it also doesn't really endear us to help you, even though over 98% of the time we're notified of inappropriate content, we side with the reporter of the content and not the author.\n" +
                "Even if they are threatening to sue the crap out of us."),
    )

    var openedIndices = remember { mutableStateOf(listOf<Int>()) }


    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(GreenGrey100)
            .background(DarkGreen10)
    ) {
        LazyColumn(modifier = Modifier.padding(0.dp)) {
            itemsIndexed(items) { index, item ->
                Row(modifier = Modifier
                    .padding(bottom = 10.dp)
                ) {


                    ExpandableContainerView(
                        itemModel = item,
                        onClickItem = { openedIndices.value = if(openedIndices.value.contains(index)) openedIndices.value.filter { it!=index } else openedIndices.value + index },
                        expanded = openedIndices.value.contains(index)
                    )
                }
            }
        }
    }
}

@Composable
fun ExpandableContainerView(itemModel: FaqItem, onClickItem: () -> Unit, expanded: Boolean) {
    Box(
        modifier = Modifier
            .background(DarkGreen90)
    ) {
        Column {
            HeaderView(questionText = itemModel.question, onClickItem = onClickItem)
            ExpandableView(answerText = itemModel.answer, isExpanded = expanded)
        }
    }
}


@Composable
fun HeaderView(questionText: String, onClickItem: () -> Unit) {
    Box(
        modifier = Modifier
            .background(GreenGrey30)
            .clickable(
                indication = null, // Removes the ripple effect on tap
                interactionSource = remember { MutableInteractionSource() }, // Removes the ripple effect on tap
                onClick = onClickItem
            )
            .padding(8.dp)
    ) {
        Text(
            text = questionText,
            fontSize = 17.sp,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ExpandableView(answerText: String, isExpanded: Boolean) {
    // Opening Animation
    val expandTransition = remember {
        expandVertically(
            expandFrom = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeIn(
            animationSpec = tween(300)
        )
    }

    // Closing Animation
    val collapseTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Top,
            animationSpec = tween(300)
        ) + fadeOut(
            animationSpec = tween(300)
        )
    }

    AnimatedVisibility(
        visible = isExpanded,
        enter = expandTransition,
        exit = collapseTransition,
        modifier = Modifier.border(
            BorderStroke(2.dp, GreenGrey30),
            shape = RoundedCornerShape(0.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .background(DarkGreen90)
                .padding(10.dp)
        ) {
            Text(
                text = answerText,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}