 
<p align="center">
<img src="doc/owlplug-logo.png">
</p>
<p align="center">
<sup>
<b>OwlPlug is an audio plugin manager. It provides a complete view of your plugin setup and simple tools to manage it.</b>
</sup>
</p>

---

[![Owlplug Main](https://github.com/DropSnorz/OwlPlug/actions/workflows/main.yml/badge.svg)](https://github.com/DropSnorz/OwlPlug/actions/workflows/main.yml)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e6b8ee875daa4f74b5bf1cc8fee6df63)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=DropSnorz/OwlPlug&amp;utm_campaign=Badge_Grade_Dashboard)
![Last Pre release](https://img.shields.io/github/release-date/dropsnorz/owlplug.svg)
[![Discord](https://img.shields.io/badge/chat-on%20discord-%237289DA.svg)](https://discord.gg/nEdHAMB)
[![Patreon](https://img.shields.io/badge/donate-%E2%99%A5-%23253b80)](https://www.paypal.com/donate?hosted_button_id=7MJGDTQXAPJ22)


# Overview

![owlplug-demo](http://dropsnorz.com/projects/owlplug/owlplug.gif)


**About OwlPlug**


:earth_africa: [OwlPlug Website](https://owlplug.com) | :pushpin: [Roadmap](https://owlplug.com/roadmap) | :page_facing_up: [Documentation](https://github.com/Dropsnorz/OwlPlug/wiki)

**Why I have created OwlPlug ?** 

OwlPlug came from my hate of installing and managing audio plugins. The process is very annoying. I always wanted something simple that looks like dependency managers or online content store. I've started this just for fun... But because it was so simple to install plugins, I've discovered dozens of cool plugins just by using my own tool. So I realized this is not just a different way to install plugins, it's a real opportunity to share and discover awesome free and open sourced plugins developed by the community. 

All kinds of feedbacks are greatly welcomed.

## Installation

### Direct download (latest version)

**Requirements**: Windows 7 (64 bit) or later, macOS High Sierra or later.

1. Browse binaries from [the release section](http://github.com/dropsnorz/owlplug/releases)
2. Download and run the OwlPlug installer for your platform
    * `.msi` installer on Windows
    * `.dmg` file on MacOS
    * `.deb` file on Linux
3. Run OwlPlug application

:warning: Mac users, depending on your OS version, you may face an error mentioning that OwlPlug is damaged. You can fix this problem using a single command described on [the wiki page](https://github.com/DropSnorz/OwlPlug/wiki/Troubleshooting).

### Package managers

#### Windows

```sh
winget install owlplug
```


## How to help

[Download and Install](https://github.com/DropSnorz/OwlPlug/releases) OwlPlug, configure it, download plugins, etc... Report any kind of problems by [opening an issue](https://github.com/DropSnorz/OwlPlug/issues) on this repository. Feel free to use the [discussions tab](https://github.com/DropSnorz/OwlPlug/discussions) to ask questions, report suggestions and feedback about features and potential improvements.

# Features

## Plugins

OwlPlug can discover VST2, VST3 and AU Plugins. OwlPlug is compatible with all previously installed plugins as long as they are all in a specific root directory, for example `C:/AudioPlugins`. Additional directories can be configured if your plugin setup is fragmented on the filesystem.

After downloading Owlplug, you can still organize (add, move, delete, ...) your plugins with a file explorer or with your favorite DAW without breaking anything.  

## Links

A Link allows you to create and manage symlinks across your filesystem and plugin directories. With Links, you can access directories anywhere on your filesystem (Hard drive, USB keys, custom directories...) through a single root plugin directory. For example, you can configure a link named *usb-drive* in `C:/AudioPlugins` to target your usb hard drive `D:/myPlugins`. All plugins in `D:/myPlugins` will be accessible using `C:/AudioPlugins/usb-drive`. This feature may be useful for DAW that scans plugins from predefined or limited number directories. On some Windows version, symlinks creation may require admin privileges.

## Explore and download Plugins

OwlPlug can be connected to several remote sources to download plugins. A Remote Source is a collection of downloadable plugins that can be installed locally. OwlPlug can be configured to use any compatible third-party store in *Explore* Tab > *Sources* > *Add a new source...*.

Here are some recommended compatible sources.

#### OwlPlug Registry ⭐ 

OwlPlug official plugin registry for Free or Open Source plugins
* kind: `registry`
* url: `https://registry.owlplug.com/registry.min.json`

#### StudioRack Registry ⭐ 

Studiorack registry plugins for OwlPlug.
* kind: `registry`
* url: `https://owlplug.github.io/studiorack-registry-adapter/registry.min.json` 

**Discover more plugin sources in [this wiki page](https://github.com/DropSnorz/OwlPlug/wiki/Remote-plugin-sources).**

### How to distribute my plugins on OwlPlug ?

* **(Recomended)** Distribute your plugins using on the official OwlPlug Registry. You can find more information on how to proceed in the [registry github repository](https://github.com/OwlPlug/owlplug-registry).

* Setup and host a custom remote source to distribute multiple plugins, following the registry specification.

* *(Deprecated)* Setup and host a custom remote source to distribute multiple plugins, following the store specification. The store specification is not actively maintained, you should use the registry spec instead.


# Development

## Stack

* Spring boot
* JavaFx & JFoenix
* Hibernate & H2
* Maven
* Juce


## Development Setup

1. Clone or download project sources
2. Run following commands
```sh
# Install dependencies
mvn clean install
# Move to owlplug client folder
cd owlplug-client
# (Optional) Create the runnable JAR file in /target/ folder
mvn clean install spring-boot:repackage
# Run owlplug
mvn spring-boot:run
```

# License

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FDropSnorz%2FOwlPlug.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FDropSnorz%2FOwlPlug?ref=badge_large)
