const DEFAULT_PROFILE_IMG = "/img/profile.png";

function safeProfileImg(src) {
    const s = (src || "").toString().trim();
    return s ? s : DEFAULT_PROFILE_IMG;
}

function applyProfileToHeader(profile) {
    const img = safeProfileImg(profile?.profileImg);

    $("#headerProfileImg, .profile-pic.profile-sty").attr("src", img);

    if (profile?.nickName) {
        $(".profile-nickname-display").text(profile.nickName);
    }
    if (profile?.profileWord) {
        $(".profileWordText").text(profile.profileWord);
    } else {
        $(".profileWordText").text("-");
    }
}


function applyProfileToDetailUI(profile) {
    const img = safeProfileImg(profile?.profileImg);

    $("#modalProfileImg").attr("src", img);

    $(".postcard-profile-img").attr("src", img);
}


function fetchMyProfile(onSuccess) {
    $.ajax({
        url: "/user/profile",
        type: "GET",
        success: function (res) {
            if (res && res.result === "success") {
                if (typeof onSuccess === "function") onSuccess(res.data || {});
            }
        }
    });
}


$(function () {
    if ($("#headerProfileImg").length || $(".profile-pic.profile-sty").length) {
        fetchMyProfile(function (p) {
            applyProfileToHeader(p);
        });
    }
});
